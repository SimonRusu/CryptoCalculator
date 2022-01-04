package Controllers;

import Views.CalculatorJFrame;
import Models.Currency;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import Data.DataMap;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class CalculatorController {
    private CalculatorJFrame calculatorJFrame;
    private SummaryController summaryController;
    private CoinGeckoApiClient client;
    private DataMap map;
    private Currency model;
    private Map<String,String> fiatMap, cryptoMap, map1, map2;
    private boolean flag;
    private ArrayList<Currency> dataList;
    
    public CalculatorController(){
        init();
        requestFocusAndSelectAll();
        textFieldListener();
        comboBoxListener();
        reverseBtnListener();
        saveBtnListener();
        summaryBtnListener();
        
        calculatorJFrame.setVisible(true);
    }
    
    public void init(){
        
        calculatorJFrame = new CalculatorJFrame();
        map = new DataMap();
        client = new CoinGeckoApiClientImpl();
        dataList = new ArrayList<>();
        
        Map<String, Map<String, Double>> value = client.getPrice("bitcoin", "usd");
        double tax = (double) value.get(value.keySet().toArray()[0]).values().toArray()[0];
        model = new Currency("bitcoin", "usd", 0, 0, tax);
        map1 = cryptoMap = map.getCryptoMap();
        map2 = fiatMap = map.getFiatMap();
        flag = true;
        
    }
    
    public void requestFocusAndSelectAll(){
        
        calculatorJFrame.getTxt1().requestFocus();
        
        calculatorJFrame.getTxt1().addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent pE) {
                calculatorJFrame.getTxt1().selectAll();
            }
        });
        
    }
    
    public void textFieldListener(){
        
        calculatorJFrame.getTxt1().addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyTyped(KeyEvent evt){
                if(Character.isLetter(evt.getKeyChar())){
                evt.consume();
                }
                else{
                    try{
                        Double.parseDouble(calculatorJFrame.getTxt1().getText()+evt.getKeyChar());
                    }
                    catch(NumberFormatException e){
                        evt.consume();
                    }
                }
            }
        });
        
        calculatorJFrame.getTxt1().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                valueUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                valueUpdate();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                valueUpdate();
            }
        });
        
        
        
    }
    
    public void comboBoxListener(){
        
         calculatorJFrame.getcBox1().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) { 
                JComboBox source = (JComboBox) ae.getSource();
                if(source.isPopupVisible()){
                    feeCalculation();
                    valueUpdate();
                }              
             }
         });
         
         calculatorJFrame.getcBox2().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) {
                JComboBox source = (JComboBox) ae.getSource();
                if(source.isPopupVisible()){
                    feeCalculation();
                    valueUpdate();
                }   
             }
         });
    }

    public void reverseBtnListener(){

        calculatorJFrame.getReverseBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               switchComboBox();
               valueUpdate();
            }
        });
    }
    
     public void saveBtnListener(){

        calculatorJFrame.getSaveBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Currency auxModel;
                try {
                    auxModel = (Currency) model.clone();
                    dataList.add(auxModel);
                } catch (CloneNotSupportedException ex) {}
                
                
            }
        });

    }
     
    public void summaryBtnListener(){

        calculatorJFrame.getSummaryBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                summaryController = new SummaryController(calculatorJFrame,dataList);
            }
        });

    }
    
    public void valueUpdate(){
        
        if(!calculatorJFrame.getTxt1().getText().isEmpty()){
            model.setValue1(Double.parseDouble(calculatorJFrame.getTxt1().getText()));
            double result = model.getValue1() * model.getFee();
            model.setValue2(result);
            calculatorJFrame.getTxt2().setText(String.valueOf(model.getValue2()));
        }
        else{
            model.setValue2(0);
            calculatorJFrame.getTxt2().setText(String.valueOf(model.getValue2()));
        }

    }
    
    public void feeCalculation(){
        
            int index = calculatorJFrame.getcBox1().getSelectedIndex();
            int index2 = calculatorJFrame.getcBox2().getSelectedIndex();
            double fee;
            
            String id1 = map1.keySet().toArray()[index].toString();
            String id2 = map2.keySet().toArray()[index2].toString();
            
            try{
                Map<String, Map<String, Double>> value = client.getPrice(id1, id2);
                fee = (double) value.get(value.keySet().toArray()[0]).values().toArray()[0];  

            }
            catch(Exception ex){
                Map<String, Map<String, Double>> value = client.getPrice(id2, id1);
                fee = (1/(double) value.get(value.keySet().toArray()[0]).values().toArray()[0]);

            }
            
            model.setFee(fee); 
            model.setType1(id1);
            model.setType2(id2);
            
    }
    
    public void switchComboBox(){
       
        String label1 = calculatorJFrame.getjLabel1().getText();
        String label2 = calculatorJFrame.getjLabel2().getText();
        int fstIndex = calculatorJFrame.getcBox1().getSelectedIndex();
        int scndIndex = calculatorJFrame.getcBox2().getSelectedIndex();
        int index1,index2;
        
        calculatorJFrame.getjLabel1().setText(label2);
        calculatorJFrame.getjLabel2().setText(label1);
        
        calculatorJFrame.getcBox1().removeAllItems();
        calculatorJFrame.getcBox2().removeAllItems();
        
        index1 = scndIndex;
        index2 = fstIndex;
            
        if(flag){
            map1 = fiatMap;
            map2 = cryptoMap;
        }
        else{
            map1 = cryptoMap;
            map2 = fiatMap;
        }
        
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            calculatorJFrame.getcBox1().addItem(entry.getValue());
        } 

        for (Map.Entry<String, String> entry : map2.entrySet()) {
            calculatorJFrame.getcBox2().addItem(entry.getValue());
        } 
        
        calculatorJFrame.getcBox1().setSelectedIndex(index1);
        calculatorJFrame.getcBox2().setSelectedIndex(index2);
        
        
        String id1 = map1.keySet().toArray()[index1].toString();
        String id2 = map2.keySet().toArray()[index2].toString();
        
        model.setType1(id1);
        model.setType2(id2);
        model.setFee(1/model.getFee());
        
        flag = !flag;
    }

}


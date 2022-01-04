package Controllers;

import Models.Currency;
import Views.SummaryJDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class SummaryController{
    private SummaryJDialog summaryJFrame;
    private JFrame frm;
    private DefaultTableModel model;
    private ArrayList<Currency> data;

    public SummaryController(JFrame frm, ArrayList<Currency> data) {
        
        init(frm, data);
        addDataToTable();
        btnListener();
        summaryJFrame.setVisible(true);
        
    }
    
    public void init(JFrame frm, ArrayList<Currency> data){
        
        this.frm = frm;
        this.data = data;
        summaryJFrame = new SummaryJDialog(frm,true);
        
    }
    
    public void addDataToTable(){
        
        model = (DefaultTableModel) summaryJFrame.getjTable1().getModel();
        for(Currency i: data){
            Object[] rowData = new Object[5];
            rowData[0] = i.getType1();
            rowData[1] = i.getValue1();
            rowData[2] = i.getType2();
            rowData[3] = i.getValue2();
            rowData[4] = i.getFee();
            
            model.addRow(rowData);
        }
    }
    
    public void btnListener(){
        summaryJFrame.getClearBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                data.clear();
                model.setRowCount(0);
            }
        });
    }
}

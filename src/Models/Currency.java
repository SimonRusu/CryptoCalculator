package Models;

public class Currency implements Cloneable{
    private double value1,value2,fee;
    private String type1,type2;

    public Currency(String type1, String type2, double value1, double value2, double fee ) {
        this.type1 = type1;
        this.type2 = type2;
        this.value1 = value1;
        this.value2 = value2;
        this.fee = fee;
    }
    
    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    @Override
    public String toString() {
        return "CurrencyModel{" + "value1=" + value1 + ", value2=" + value2 + ", fee=" + fee + ", type1=" + type1 + ", type2=" + type2 + '}';
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
}

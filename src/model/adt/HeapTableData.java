package model.adt;

public class HeapTableData {
    private Integer address;
    private String value;
    public HeapTableData(Integer a, String v){
        this.address = a;
        this.value = v;
    }

    public Integer getAddress(){return address;}

    public String getValue(){return value;}

    public String toString(){
        return Integer.toString(address) + " " + value.toString();
    }
}

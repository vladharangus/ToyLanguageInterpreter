package model.adt;

public class SymbolTableData {
    private String var_name;
    private String value;

    public SymbolTableData(String var, String v){
        this.value = v;
        this.var_name = var;
    }

    public String getVar_name(){return var_name;}

    public String getValue(){return value;}

    public String toString(){
        return var_name + " " + value.toString();
    }
}

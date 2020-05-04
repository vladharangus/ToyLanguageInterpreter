package model.exp;

import exeptions.MyExeption;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.Type;
import model.value.Value;

public class VarExp implements Exp {
    private String id;
    public VarExp(String id) {this.id = id;}
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyExeption
    {
        return tbl.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        return typeEnv.lookup(id);
    }

    public String toString() {
        return id;
    }
}

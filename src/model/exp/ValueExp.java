package model.exp;
import exeptions.*;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.Type;
import model.value.Value;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e) {this.e = e;}
    public String toString() {return "(" + e.toString() + ")";}
    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyExeption {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        return e.getType();
    }
}

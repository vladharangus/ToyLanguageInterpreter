package model.exp;
import exeptions.*;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.Type;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyExeption;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyExeption;
}

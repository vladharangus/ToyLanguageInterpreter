package model.exp;

import exeptions.MyExeption;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class rH implements Exp {
    private Exp expression;
    public rH(Exp e) {expression = e;}
    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyExeption {
        Value v = expression.eval(tbl ,hp);
        if(v.getType() instanceof RefType)
        {
            int a = ((RefValue) v).getAddr();
            Value val = hp.getValue(a);
            if(val == null)
                throw new MyExeption("not such key");
            return val;
        }
        throw new MyExeption("Not ref type");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyExeption("the rH argument is not a Ref Type");
    }

    public String toString()
    {
        return "rH(" + expression + ")";
    }
}

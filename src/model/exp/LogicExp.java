package model.exp;
import exeptions.*;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private char op;
    public LogicExp(Exp e1, Exp e2, char op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    public String toString() {
        return "(" + e1.toString() + op + e2.toString() + ")/n";
    }
    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyExeption {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if(v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, hp);
            if(v2.getType().equals(new BoolType()))
            {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if(op == '&') return new BoolValue(n1 &n2);
                if(op == '|') return new BoolValue(n1 | n2);
            }

                throw new MyExeption("second operand is not boolean");
        }

            throw new MyExeption("first operand is not boolean");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyExeption("second operand is not a boolean");
        }else
            throw new MyExeption("first operand is not a boolean");
    }
}

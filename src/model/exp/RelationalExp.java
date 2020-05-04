package model.exp;

import exeptions.MyExeption;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExp implements Exp {
    private Exp ex1;
    private Exp ex2;
    private String op;
    public RelationalExp(Exp ex1, Exp ex2, String op) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.op = op;
    }
    public String toString() {
        return "(" + ex1.toString() + op + ex2.toString() + ")/n";
    }
    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyExeption {
        Value v1, v2;
        v1 = ex1.eval(tbl, hp);
        if(v1.getType().equals(new IntType())) {
            v2 = ex2.eval(tbl, hp);
            if(v2.getType().equals(new IntType()))
            {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if(op.equals("<") ) return new BoolValue(n1 < n2);
                if(op.equals("<=")) return new BoolValue(n1 <= n2);
                if(op.equals("==")) return new BoolValue(n1 == n2);
                if(op.equals("!=")) return new BoolValue(n1 != n2);
                if(op.equals(">")) return new BoolValue(n1 > n2);
                if(op.equals(">=")) return new BoolValue(n1 >= n2);
                    if(n2 == 0) throw new MyExeption("division by 0");
                    else return new IntValue(n1 / n2);
            }

            throw new MyExeption("second operand is not integer");
        }
        throw new MyExeption("first operand is not integer");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typ1, typ2;
        typ1=ex1.typecheck(typeEnv);
        typ2=ex2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyExeption("second operand is not an integer");
        }else
            throw new MyExeption("first operand is not an integer");
    }
}

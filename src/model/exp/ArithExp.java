package model.exp;
import exeptions.*;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private char op;
    public ArithExp(Exp e1, Exp e2, char op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;//1-plus, 2-minus, 3-star, 4- devide
    }

    public String toString() {
        return "(" + e1.toString() + " " + op + " " + e2.toString() + ")";
    }

    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyExeption{
        Value v1, v2;
        v1 = e1.eval(tbl,hp);
        if(v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if(v2.getType().equals(new IntType()))
            {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if(op == '+') return new IntValue(n1 + n2);
                if(op == '-') return new IntValue(n1 - n2);
                if(op == '*') return new IntValue(n1 * n2);
                if(op == '/')
                    if(n2 == 0) throw new MyExeption("division by 0");
                    else return new IntValue(n1/ n2);
            }

                throw new MyExeption("second operand is not integer");
        }
            throw new MyExeption("first operand is not integer");

    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyExeption("second operand is not an integer");
        }else
        throw new MyExeption("first operand is not an integer");
    }
}

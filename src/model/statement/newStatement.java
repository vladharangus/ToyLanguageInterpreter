package model.statement;

import exeptions.MyExeption;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.exp.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

import java.sql.Statement;

public class newStatement implements IStatement {
    private String var_name;
    private Exp expression;
    public newStatement(String name, Exp e) { var_name = name; expression = e;}
    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(symTbl.isDefined(var_name) && symTbl.getValue(var_name).getType() instanceof RefType)
        {
            RefValue v1 = (RefValue)symTbl.getValue(var_name);
            RefType type = (RefType)v1.getType();
            Value v = expression.eval(symTbl,heap);
            if(v.getType().equals(type.getInner()))
            {
                int location = heap.getFreeLocation();
                heap.add(location, v);
                RefValue newvalue = new RefValue(location, type.getInner());
                symTbl.update(var_name, newvalue);
            }
            else throw new MyExeption("Types not equal");
        }
        else throw new MyExeption("Variable already defined or is not RefType");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new newStatement(var_name, expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {

            Type typevar = typeEnv.lookup(var_name);
            Type typexp = expression.typecheck(typeEnv);
            if (typevar.equals(new RefType(typexp)))
                return typeEnv;
            else
                throw new MyExeption("NEW stmt: right hand side and left hand side have different types ");
    }

    public String toString()
    {
        return "new(" + var_name + ", " + expression + ")";
    }
}

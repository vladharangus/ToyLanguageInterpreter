package model.statement;

import model.ProgramState;
import model.adt.*;
import model.exp.Exp;
import model.type.Type;
import model.value.Value;

import javax.swing.*;
import exeptions.*;

public class AssignStatement implements IStatement {
    private String id;
    private Exp exp;

    public AssignStatement(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }
    public String toString() {
        return "(" + id.toString() + "=" + exp.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if (symTbl.isDefined(id)) {
            Type typId = (symTbl.getValue(id)).getType();
            if(val.getType().equals(typId))
                    symTbl.update(id, val);
            else throw new MyExeption("declared type of variable" + id + " and type of the expression do not match");

        }
        else throw new MyExeption("the used variable " + id + " was not delcared before");
        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(id, exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyExeption("Assignment: right hand side and left hand side have different types ");
    }
}

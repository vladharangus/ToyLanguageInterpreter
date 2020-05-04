package model.statement;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.exp.Exp;
import model.type.*;
import model.value.*;
import exeptions.*;

public class IfStatement implements IStatement{
    private Exp exp;
    private IStatement thenS;
    private IStatement elseS;

    public IfStatement(Exp exp, IStatement then, IStatement elses)
    {
       this.exp = exp;
       this.thenS = then;
       this.elseS = elses;
    }

    public String toString() {
        return "IF(" + exp.toString() + ") THEN(" + thenS.toString() +") ELSE(" + elseS.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if(val.getType().equals(new IntType()))
            throw new MyExeption("Expresion is not boolean!\n");
        else if (val.getType().equals(new BoolType()))
        {
            BoolValue val1 = (BoolValue)val;
            if (val1.getValue())
                stk.push(thenS);
            else
                stk.push(elseS);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(exp, thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyExeption("The condition of IF has not the type bool");
    }
}

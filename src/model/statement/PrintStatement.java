package model.statement;
import exeptions.*;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.exp.Exp;
import model.type.Type;
import model.value.Value;

public class PrintStatement implements IStatement{
    private Exp exp;

    public PrintStatement(Exp exp) { this.exp = exp; }
    public String toString() { return "print(" + exp.toString() + ")";}

    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIList<Value> out = state.getOut();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        out.add(val);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public Exp getPrintExpression() {return this.exp;}
}

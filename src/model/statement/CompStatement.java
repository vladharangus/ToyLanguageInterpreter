package model.statement;
import model.adt.*;
import model.ProgramState;
import exeptions.*;
import model.type.Type;

public class CompStatement implements IStatement{
    private IStatement first, second;

    public CompStatement(IStatement first, IStatement second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public IStatement getFirst() {return this.first;}
    public IStatement getSecond() {return this.second;}
}

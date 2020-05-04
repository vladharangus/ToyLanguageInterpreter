package model.statement;
import model.ProgramState;
import exeptions.*;
import model.adt.MyIDictionary;
import model.type.Type;

public class NopStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        return typeEnv;
    }
}

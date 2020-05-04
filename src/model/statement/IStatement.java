package model.statement;
import model.ProgramState;
import exeptions.*;
import model.adt.MyIDictionary;
import model.type.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyExeption;
    IStatement deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyExeption;
}

package model.statement;

import exeptions.MyExeption;
import model.ProgramState;
import model.adt.*;
import model.type.BoolType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.Map;

public class forkStatement implements IStatement {
    private IStatement stmt;
    public forkStatement(IStatement s) {
        stmt = s;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTbl();
        MyIList<Value> out = state.getOut();

        MyIStack<IStatement> newstack = new MyStack<>();
        MyIDictionary<String, Value> newsymTable = new MyDictionary<>();
        for(Map.Entry<String, Value> entry: symTbl.getDictionary().entrySet())
        {
            newsymTable.update(new String(entry.getKey()),entry.getValue().deepCopy());
        }
        return new ProgramState(newstack,newsymTable,out,FileTable,stmt,heap);
    }

    @Override
    public IStatement deepCopy() {
        return new forkStatement(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
            stmt.typecheck(typeEnv.clone());
            return typeEnv;
    }

    public String toString()
    {
        return "fork(" + stmt.toString() + ")";
    }
}

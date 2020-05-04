package model.statement;

import exeptions.MyExeption;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exp.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement {
    private Exp filename;
    public CloseRFile(Exp fn) {filename = fn;}
    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTbl();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value v = filename.eval(symTbl, heap);
        if(v.getType().equals(new StringType()))
        {
            BufferedReader buffer = FileTable.getValue((StringValue) v);
            if(buffer != null)
                try
                {
                    buffer.close();
                    FileTable.remove((StringValue) v);
                }
                catch (IOException e)
                {
                    throw new MyExeption("Couldn't close buffer");
                }
            else throw new MyExeption("There is no entry of this buffer");
        } else throw new MyExeption("The expresion is not a string valaue");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(filename);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typ = filename.typecheck(typeEnv);
        if(typ.equals(new StringType()))
        {
            return typeEnv;
        }
        else throw new MyExeption("Filename is not a string");
    }

    public String toString()
    {
        return "closeFile(" + filename.toString() + ")";
    }
}

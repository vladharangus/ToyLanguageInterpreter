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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStatement {
    private Exp filename;
    public OpenRFile(Exp filename) {
        this.filename = filename;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTbl();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value v = filename.eval(symTbl, heap);
        if(v.getType().equals(new StringType()))
        {
            StringValue s = (StringValue)v;
            if(!FileTable.isDefined(s))
                try
                {
                    BufferedReader buffer = new BufferedReader(new FileReader(s.getValue()));

                    FileTable.add(s, buffer);
                }
                catch (FileNotFoundException e) {
                    //System.out.println(e.getMessage());
                    throw new MyExeption("Couldn't create buffer");
                }
            else throw new MyExeption("Key already defined");
        } else throw new MyExeption("The expresion is not a string valaue");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFile(filename);
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
        return "openFile(" + filename.toString() + ")";
    }

}

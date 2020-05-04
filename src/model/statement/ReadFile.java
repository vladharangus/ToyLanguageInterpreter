package model.statement;

import exeptions.MyExeption;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.exp.Exp;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private Exp exp;
    private String var_name;
    public ReadFile(Exp exp, String var) {
        this.exp = exp;
        var_name = var;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTbl();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(symTbl.isDefined(var_name) && symTbl.getValue(var_name).getType().equals(new IntType()))
        {
            Value v = exp.eval(symTbl, heap);
            if(v.getType().equals(new StringType()))
            {
                BufferedReader buffer = FileTable.getValue((StringValue) v);
                if(buffer != null)
                {
                    IntValue valueFromfile;
                    try
                    {
                        String result = buffer.readLine();
                        if(result == null) {
                            valueFromfile = new IntValue(0);
                        }
                        else
                        {
                            valueFromfile = new IntValue(Integer.parseInt(result));
                        }
                        symTbl.update(var_name, valueFromfile);
                    }
                    catch (IOException e)
                    {
                        throw new MyExeption("Couldn't read the line");
                    }
                }
                else throw new MyExeption("No entry for this buffer");
            }
            else throw new MyExeption("Value not a string type");
        }
        else throw new MyExeption("Variable not of type int");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(exp, var_name);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typ = exp.typecheck(typeEnv);
        Type t = typeEnv.lookup(var_name);
        if(typ.equals(new StringType()))
        {
            if(t.equals(new IntType()))
                return typeEnv;
            else throw new MyExeption("Variable is not of int type");
        }
        else throw new MyExeption("Filename is not a string");

    }

    public String toString()
    {
        return "readFile(" + exp.toString() +  " " + var_name + ")";
    }
}

package model.statement;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import exeptions.*;
public class VarDeclStatement implements IStatement{
    private String name;
    private Type typ;

    public VarDeclStatement(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    public String toString() {
        return "(" + typ.toString() + " " + name + ";)";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(name))
            throw new MyExeption("Var already defined");
        else
            if (typ instanceof IntType)
                symTbl.add(name, typ.defaultValue());
            else if(typ instanceof BoolType)
                symTbl.add(name, typ.defaultValue());
            else if(typ instanceof StringType)
                symTbl.add(name, typ.defaultValue());
            else if(typ instanceof RefType)
                symTbl.add(name, typ.defaultValue());
        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(name, typ);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        typeEnv.add(name,typ);
        return typeEnv;
    }
}

package model.statement;

import exeptions.MyExeption;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.exp.Exp;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class wH implements IStatement {
    private String var_name;
    public Exp exp;
    public String toString()
    {
        return "wH(" + var_name + " " + exp +  ")";
    }
    public wH(String var, Exp e) { var_name = var; exp = e;}
    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(symTbl.isDefined(var_name) && symTbl.getValue(var_name).getType() instanceof RefType)
        {
            RefValue value = (RefValue) symTbl.getValue(var_name);
            int address = value.getAddr();
            if(heap.isDefined(address))
            {
                Value v = exp.eval(symTbl,heap);
                RefType type = (RefType)symTbl.getValue(var_name).getType();
                if(v.getType().equals(type.getInner()))
                {
                    heap.update(address, v);
                } else throw new MyExeption("Types don't match");
            } else throw new MyExeption("Not defined");
        } else throw new MyExeption("Not defined in symbol table or not ref type");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new wH(var_name, exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typ = exp.typecheck(typeEnv);
        Type t = typeEnv.lookup(var_name);
        if(t instanceof RefType) {
            RefType rt = (RefType) t;
            if(typ.equals(((RefType) t).getInner()))
                return typeEnv;
            else throw new MyExeption("Expression does not have the inner type");
        } else throw new MyExeption("Variable is not of ref type");
    }
}

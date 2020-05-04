package model.statement;


import exeptions.MyExeption;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.exp.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class whileStatement implements IStatement {
    private Exp exp;
    private IStatement stmt;

    public whileStatement(Exp e, IStatement s){
        this.exp = e;
        this.stmt = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExeption {
        MyIStack<IStatement> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if(val.getType().equals(new BoolType())) {
            BoolValue b = (BoolValue) val;
            if (b.getValue()){
                stk.push(this);
                stk.push(stmt);

            }
        }else throw new MyExeption("condition exp is not a boolean");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new whileStatement(exp, stmt.deepCopy()) ;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyExeption {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyExeption("The condition of WHILE has not the type bool");
    }

    public String toString()
    {
        return "WHILE(" + exp + ")" + stmt;
    }
}
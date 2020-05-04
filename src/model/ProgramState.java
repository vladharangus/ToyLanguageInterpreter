package model;

import exeptions.MyExeption;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statement.IStatement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> FileTbl;
    private IStatement originalProgram;
    private MyIHeap<Integer, Value> heap;
    private static int id = 0;
    private int ID;

    public ProgramState(MyIStack<IStatement> stk, MyIDictionary<String, Value> symtable,
                        MyIList<Value> ot, MyIDictionary<StringValue, BufferedReader> FileTbl,IStatement prg, MyIHeap<Integer, Value> h)
    {
        ID = getNewId();
        exeStack = stk;
        symTable = symtable;
        out = ot;
        this.FileTbl = FileTbl;
        originalProgram = prg.deepCopy();
        heap = h;
        stk.push(prg);
    }
    private synchronized int getNewId()
    {
        ++id;
        return id;
    }
    public Boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public int getId() {return ID;}
    public ProgramState oneStep() throws MyExeption {
        if(exeStack.isEmpty()) throw new MyExeption("prgstate stack is empty");
        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public MyIHeap<Integer, Value> getHeap() {return heap;}
    public void setHeap(MyIHeap<Integer, Value> h) {heap = h;}

    public MyIStack<IStatement> getStack() {
        return this.exeStack;
    }
    public void setStack(MyIStack<IStatement> exeStack) {this.exeStack = exeStack;}

    public MyIDictionary<String, Value> getSymTable() {return this.symTable;}
    public void setSymTable(MyIDictionary<String, Value> symTable) {this.symTable = symTable;}

    public MyIList<Value> getOut() {return this.out;}
    public void setOut(MyIList<Value> out) {this.out = out;}

    public IStatement getOriginalProgram() {return  this.originalProgram;}
    public void setOriginalProgram(IStatement prg) {this.originalProgram = prg;}

    public MyIDictionary<StringValue, BufferedReader> getFileTbl() {return FileTbl;}
    public void setFileTbl(MyIDictionary<StringValue, BufferedReader> filtbl) {FileTbl = filtbl;}

    public String toString()
    {
        return "Program id:" + id + "\nExecution stack: " + exeStack.toString() + "\nSymbol Table:" + symTable.toString() + "\nProgram Output:" + out.toString() + "\n" + FileTbl.toString() + "\n";
    }
}

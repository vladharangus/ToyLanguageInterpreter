package repository;

import model.ProgramState;
import model.adt.*;
import model.statement.IStatement;
import model.value.StringValue;
import model.value.Value;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class Repository implements IRepository{
    private List<ProgramState> myPrgStates;
    private String logfilePath;
    public Repository(ProgramState prg, String path) {
        myPrgStates = new ArrayList<>();
        myPrgStates.add(prg);
        logfilePath = path;
    }

    @Override
    public void addPrg(ProgramState newprg) {
        myPrgStates.add(newprg);
    }

    @Override
    public void logProgramStateExec(ProgramState prg) throws IOException {
        PrintWriter logfile;
        logfile = new PrintWriter(new BufferedWriter(new FileWriter(logfilePath, true)));
        MyIStack<IStatement> stack = prg.getStack();
        MyIDictionary<String, Value> symtbl = prg.getSymTable();
        MyIList<Value> out = prg.getOut();
        MyIDictionary<StringValue, BufferedReader> filetbl = prg.getFileTbl();
        MyIHeap<Integer, Value> heap = prg.getHeap();
        ArrayList<IStatement> a = new ArrayList<IStatement>(stack.getStack());
        ListIterator<IStatement> li = a.listIterator(a.size());
        logfile.println("ID: " + prg.getId());
        logfile.println("ExeStack:");
        while(li.hasPrevious())
        {
            logfile.println("-> " + li.previous().toString());
        }

        logfile.println("SymTable:");
        for(HashMap.Entry<String, Value> e: symtbl.getDictionary().entrySet())
        {
            logfile.println(e.getKey() + "-> " + e.getValue().toString());
        }

        logfile.println("Out:");
        for(Value e : out.getQueue())
            logfile.println(e.toString());

        logfile.println("FileTable:");
        for(HashMap.Entry<StringValue, BufferedReader> e : filetbl.getDictionary().entrySet())
        {
            logfile.println(e.getKey().toString());
        }
        logfile.println("HeapTable:");
        for(HashMap.Entry<Integer, Value> e : heap.getDictionary().entrySet())
        {
            logfile.println(e.getKey().toString() + "->" + e.getValue().toString());
        }
        logfile.println(".......................................");
        logfile.close();
    }

    @Override
    public List<ProgramState> getPrgList() {
        return myPrgStates;
    }

    @Override
    public void setPrgList(List<ProgramState> list) {
        myPrgStates = list;
    }

    @Override
    public ProgramState getByID(Integer id) {
        for(ProgramState p : this.myPrgStates)
        {
            if(p.getId() == id)
                return p;
        }
        return null;
    }
}

package controller;

import exeptions.MyExeption;
import model.ProgramState;
import model.adt.MyIStack;
import model.adt.MyList;
import model.statement.IStatement;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository myRepository;
    private ExecutorService executor;
    public IRepository getMyRepository(){
        return myRepository;
    }
    private Map<Integer, Value> ConservativeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value>heap)
    {
        Map<Integer, Value> map = heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer, Value> result = new HashMap<Integer, Value>();
        for(Map.Entry<Integer, Value> entry : map.entrySet())
        {
            result.put(entry.getKey(), entry.getValue());
        }

        Map<Integer, Value> mpt = new HashMap<Integer, Value>();
        for(Map.Entry<Integer, Value> entry : heap.entrySet())
        {
            mpt.put(entry.getKey(), entry.getValue());
        }

        for(Map.Entry<Integer, Value> entry : map.entrySet())
        {
            Value v = entry.getValue();

            while(v instanceof RefValue)
            {
                RefValue rv = (RefValue)v;
                int adr = rv.getAddr();
                Value v2 = mpt.get(adr);
                result.put(adr, v2);
                v = v2;
            }
        }
        return result;
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }


    public Controller(IRepository myRepository){
        this.myRepository = myRepository;

    }

    private List<ProgramState> removeCompletedProgram(List<ProgramState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    private void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());
        List<ProgramState> newPrgList = executor.invokeAll(callList).stream().map(future->{try{return future.get();}catch (InterruptedException  |
            ExecutionException e) { e.getMessage();} return null;}).filter(p-> p!= null).collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg-> {
            try {
                myRepository.logProgramStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        myRepository.setPrgList(prgList);
    }

    public void addProgram(ProgramState newprg) {
        myRepository.addPrg(newprg);
    }
    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedProgram(myRepository.getPrgList());
        while (prgList.size() > 0) {

            oneStepForAllPrg(prgList);
            prgList = removeCompletedProgram(myRepository.getPrgList());
        }
        executor.shutdownNow();
        myRepository.setPrgList(prgList);
    }
    public List<ProgramState> allStepGUI2() throws Exception {
        List<ProgramState>programsList = removeCompletedProgram(myRepository.getPrgList());
        if (programsList.size()>0){
            oneStepForAllPrg(programsList);
        }
        else{
            executor.shutdownNow();
            throw new Exception("Program is done\n");
        }
        myRepository.setPrgList(programsList);
        return this.myRepository.getPrgList();
    }
    public void allStepGUI(){
        executor = Executors.newFixedThreadPool(2);
    }


}

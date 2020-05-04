package repository;

import model.ProgramState;
import model.adt.MyList;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void addPrg(ProgramState newprg);
    void logProgramStateExec(ProgramState prg) throws IOException;
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> list);
    ProgramState getByID(Integer id);
}

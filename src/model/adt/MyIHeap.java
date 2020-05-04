package model.adt;

import exeptions.MyExeption;

import java.util.HashMap;
import java.util.Map;

public interface MyIHeap<T, T1> {
    public int getFreeLocation();
    int getNewLocation();
    boolean isDefined(T id);

    void update(T id, T1 val);

    T1 getValue(T id);

    void add(T name, T1 i);

    void remove(T id);

    HashMap<T, T1> getDictionary();
    void setContent(Map<T, T1> M) ;
    HashMap<T, T1> getContent();
    public T1 lookup(T id) throws MyExeption;

}

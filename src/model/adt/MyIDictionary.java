package model.adt;
import exeptions.*;
import model.value.IntValue;

import java.util.HashMap;

public interface MyIDictionary<T, T1> {
    boolean isDefined(T id);

    void update(T id, T1 val);

    T1 getValue(T id);

    void add(T name, T1 i);

    void remove(T id);

    HashMap<T, T1> getDictionary();

    public T1 lookup(T id) throws MyExeption;
    MyIDictionary<T, T1> clone();
}

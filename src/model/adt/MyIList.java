package model.adt;

import java.util.Queue;

public interface MyIList<T> {
    void add(T v);
    T pop();
    Queue<T> getQueue();
    String toString();
}

package model.adt;

import java.util.LinkedList;
import java.util.Queue;

public class MyList<T> implements MyIList<T> {
    Queue<T> list;

    public MyList() {
        list = new LinkedList<T>();
    }

    @Override
    public void add(T v) {
        list.add(v);

    }

    @Override
    public T pop() {
        return list.poll();
    }

    @Override
    public Queue<T> getQueue() {
        return list;
    }

    public String toString() {
        String result = "";
        for(T e: list)
        {
            result += e.toString() + "\n";
        }
        return result;
    }
    public Queue<T> getList() {return list;}
}

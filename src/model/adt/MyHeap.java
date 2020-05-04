package model.adt;

import exeptions.MyExeption;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<T, T1> implements MyIHeap<T, T1> {
    private HashMap<T, T1> dictionary;
    private int freeLocation;
    private static int newloc = 0;
    public MyHeap() {
        dictionary = new HashMap<T, T1>();
        freeLocation = getNewLocation();
    }
    public int getFreeLocation() {return freeLocation;}
    public synchronized int getNewLocation()
    {
        ++newloc;
        return newloc;
    }
    @Override
    public boolean isDefined(T id) {
        if(dictionary.get(id) != null)
            return true;
        else
            return false;
    }

    @Override
    public synchronized void update(T id, T1 val) {
        dictionary.put(id, val);
    }

    @Override
    public synchronized T1 getValue(T id) {
        return dictionary.get(id);
    }

    @Override
    public synchronized void add(T name, T1 i) {
        dictionary.put(name, i);
        freeLocation = getNewLocation();
    }

    @Override
    public void remove(T id) {
        dictionary.remove(id);
        freeLocation = (int)id;
    }

    @Override
    public HashMap<T, T1> getDictionary() {
        return dictionary;
    }

    @Override
    public void setContent(Map<T, T1> M) {
        this.dictionary = (HashMap<T, T1>) M;
    }

    @Override
    public HashMap<T, T1> getContent() {
        return this.dictionary;
    }

    @Override
    public synchronized T1 lookup(T id) throws MyExeption {
        if(dictionary.get(id) != null)
            return dictionary.get(id);
        throw new MyExeption("Couldn't find the given id");
    }
    public String toString()
    {
        String result = "";
        Set<T> keys = dictionary.keySet();
        for(T k: keys)
            result += k.toString() + ":" + dictionary.get(k).toString() + " ";
        return result;
    }
}

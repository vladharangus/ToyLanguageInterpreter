package model.adt;
import exeptions.*;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<T, T1> implements MyIDictionary<T, T1> {
    private HashMap<T, T1> dictionary;
    public MyDictionary() {
        dictionary = new HashMap<T, T1>();
    }
    @Override
    public boolean isDefined(T id) {
        if(dictionary.get(id) != null)
            return true;
        else
            return false;
    }

    @Override
    public void update(T id, T1 val) {
        dictionary.put(id, val);

    }

    @Override
    public T1 getValue(T id) {
        return dictionary.get(id);
    }

    @Override
    public void add(T name, T1 i) {
        dictionary.put(name, i);

    }

    @Override
    public void remove(T id) {
        dictionary.remove(id);
    }

    @Override
    public HashMap<T, T1> getDictionary() {
        return dictionary;
    }

    @Override
    public T1 lookup(T id) throws MyExeption {
        if(dictionary.get(id) != null)
            return dictionary.get(id);
        throw new MyExeption("Couldn't find the given id");
    }

    @Override
    public MyIDictionary<T, T1> clone() {
        MyIDictionary<T, T1> clone = new MyDictionary<T, T1>();
        for(T key:dictionary.keySet())
        {
            try {
                clone.add(key,dictionary.get(key));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return clone;
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

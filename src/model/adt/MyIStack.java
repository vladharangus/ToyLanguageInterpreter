package model.adt;

import java.util.Stack;

public interface MyIStack<T> {
    void push(T second);
    T pop();
    boolean isEmpty();
    Stack<T> getStack();
    Stack<?> clone();
}

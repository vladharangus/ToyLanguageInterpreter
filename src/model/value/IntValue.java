package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
    private int val;
    public IntValue(int v) {val = v;}

    public int getValue() {
        return val;
    }
    public String toString(){
        return "" + val + "";
    }
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }


    public boolean equals(Object another) {
        if(another instanceof IntValue)
            return true;
        else
            return false;
    }
}

package model.value;

import model.type.*;

public class BoolValue implements Value {
    private boolean val;
    public BoolValue(boolean v) {val = v;}

    public boolean getValue() {
        return val;
    }
    public String toString(){
        return "" + val + "";
    }
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    public boolean equals(Object another) {
        return another instanceof BoolValue;
    }
}

package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    private String val;
    public StringValue(String v) {val = v;}

    public String getValue() {
        return val;
    }
    public String toString(){
        return val;
    }
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }


    public boolean equals(Object another) {
        return another instanceof StringValue;
    }
}

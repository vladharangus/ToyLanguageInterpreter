package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;
    public RefValue(int a, Type l) {address = a; locationType = l;}
    public String toString()
    {
        return "(" + address + " " + locationType + ")";
    }
    public int getAddr() {return address;}
    public Type getType() { return new RefType(locationType);}

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }


}

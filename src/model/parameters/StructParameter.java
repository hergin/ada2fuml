package model.parameters;

import model.Parameter;
import model.enums.DirectionEnum;
import model.Struct;

public class StructParameter extends Parameter {

    private Struct type;

    public StructParameter (String name, DirectionEnum direction, Struct type) {
        super(name, direction);
        this.type = type;
    }

    public void fixType(Struct type) {
        this.type = type;
    }

    public Struct getType() {
        return type;
    }
}

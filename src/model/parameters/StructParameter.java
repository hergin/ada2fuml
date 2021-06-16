package model.parameters;

import model.AbstractParameter;
import model.enums.DirectionEnum;
import model.Struct;

public class StructParameter extends AbstractParameter {

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

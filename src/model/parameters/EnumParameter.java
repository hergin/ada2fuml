package model.parameters;

import model.Parameter;
import model.enums.DirectionEnum;
import model.Enumeration;

public class EnumParameter extends Parameter {

    private Enumeration type;

    public EnumParameter (String name, DirectionEnum direction, Enumeration type) {
        super(name, direction);
        this.type = type;
    }

//    public void fixType(Enumeration type) {
//        this.type = type;
//    }

    public Enumeration getType() {
        return type;
    }
}

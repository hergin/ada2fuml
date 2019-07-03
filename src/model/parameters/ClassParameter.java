package model.parameters;

import model.Parameter;
import model.enums.DirectionEnum;
import model.Class;

public class ClassParameter extends Parameter {

    private Class type;

    public ClassParameter (String name, DirectionEnum direction, Class type) {
        super(name, direction);
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}

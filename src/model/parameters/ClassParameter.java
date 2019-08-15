package model.parameters;

import model.Parameter;
import model.enums.DirectionEnum;
import model.Class;

public class ClassParameter extends Parameter {

    private Class type;

    private String placeholder;

    public ClassParameter (String name, DirectionEnum direction, String placeholder) {
        super(name, direction);
        this.placeholder = placeholder;
    }

    public ClassParameter (String name, DirectionEnum direction, Class type) {
        super(name, direction);
        this.type = type;
    }

    public void fixType(Class type) {
        this.type = type;
        this.placeholder = "";
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Class getType() {
        return type;
    }
}

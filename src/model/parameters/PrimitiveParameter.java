package model.parameters;

import model.Parameter;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;

public class PrimitiveParameter extends Parameter {

    private TypeEnum type;
    private Object defaultValue;

    public PrimitiveParameter(String name, DirectionEnum direction, TypeEnum type){
        super(name, direction);
        this.type = type;
    }

    public PrimitiveParameter(String name, DirectionEnum direction, TypeEnum type, Object defaultValue){
        super(name, direction);
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public TypeEnum getType() {
        return type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}

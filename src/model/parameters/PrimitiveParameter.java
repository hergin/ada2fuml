package model.parameters;

import model.AbstractParameter;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;

public class PrimitiveParameter extends AbstractParameter {

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
        if(defaultValue!=null && defaultValue instanceof String && ((String) defaultValue).trim().equals("\"\""))
            this.defaultValue = "";
    }

    public boolean hasDefault() {
        return defaultValue != null && !defaultValue.toString().isEmpty();
    }

    public TypeEnum getType() {
        return type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}

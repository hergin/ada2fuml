package model.properties;

import model.AbstractProperty;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;

public class PrimitiveProperty extends AbstractProperty {

    private TypeEnum type;
    private Object defaultValue;

    public PrimitiveProperty(String name, VisibilityEnum visibility, TypeEnum type, Object defaultValue) {
        super(name,visibility);
        this.type = type;
        this.defaultValue = defaultValue;
        if(defaultValue!=null && defaultValue instanceof String && ((String) defaultValue).trim().equals("\"\""))
            this.defaultValue = "";
    }

    public TypeEnum getType() {
        return type;
    }

    public boolean hasDefault() {
    	return defaultValue != null && !defaultValue.toString().isEmpty();
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}

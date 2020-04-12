package model.properties;

import model.Property;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;

public class PrimitiveProperty extends Property {

    private TypeEnum type;
    private Object defaultValue;

    public PrimitiveProperty(String name, VisibilityEnum visibility, TypeEnum type, Object defaultValue) {
        super(name,visibility);
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public TypeEnum getType() {
        return type;
    }

    public boolean hasDefault() {
    	return defaultValue != null;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}

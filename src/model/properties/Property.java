package model.properties;

import model.AbstractProperty;
import model.enums.VisibilityEnum;

public class Property extends AbstractProperty {

    private String reference;
    private Object value;

    public Property() {
        super("", VisibilityEnum.Public);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

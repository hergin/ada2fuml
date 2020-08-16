package model.properties;

import model.Enumeration;
import model.EnumerationLiteral;
import model.Property;
import model.enums.VisibilityEnum;

public class EnumerationProperty extends Property {

    private Enumeration type;
    private EnumerationLiteral defaultValue;
    private String placeholder;

    public EnumerationProperty(String name, Enumeration type) {
        super(name, VisibilityEnum.Public);
        this.type = type;
    }

    public EnumerationProperty(String name, String placeholder) {
        super(name, VisibilityEnum.Public);
        this.placeholder = placeholder;
    }

    public EnumerationProperty(String name, String placeholder, EnumerationLiteral defaultValue) {
        super(name, VisibilityEnum.Public);
        this.placeholder = placeholder;
        this.defaultValue = defaultValue;
    }

    public EnumerationLiteral getDefaultValue() {
        return defaultValue;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Enumeration getType() {
        return type;
    }
}

package model.properties;

import model.Enumeration;
import model.Property;
import model.enums.VisibilityEnum;

public class EnumerationProperty extends Property {

    private Enumeration enumeration;

    public EnumerationProperty(String name, Enumeration enumeration) {
        super(name, VisibilityEnum.Public);
        this.enumeration = enumeration;
    }

    public Enumeration getEnumeration() {
        return enumeration;
    }
}

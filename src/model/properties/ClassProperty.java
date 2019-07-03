package model.properties;

import model.Class;
import model.Property;
import model.enums.VisibilityEnum;

public class ClassProperty extends Property {

    private Class type;

    public ClassProperty(String name, VisibilityEnum visibility, Class type) {
        super(name, visibility);
        this.type = type;
    }

    public Class getType() {
        return type;
    }

}

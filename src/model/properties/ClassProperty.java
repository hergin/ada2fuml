package model.properties;

import model.Class;
import model.Property;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;

public class ClassProperty extends Property implements IPlaceholderedElement {

    private Class type;

    private String placeholder;

    public ClassProperty(String name, VisibilityEnum visibility, String placeholder) {
        super(name, visibility);
        this.placeholder = placeholder;
    }

    public ClassProperty(String name, VisibilityEnum visibility, Class type) {
        super(name, visibility);
        this.type = type;
    }

    public void fixType(Class type) {
        this.type = type;
        this.placeholder = "";
    }

    public Class getType() {
        return type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void fixType(HierarchicalElement theReplacement) {
        fixType(((Class) theReplacement));
    }

    @Override
    public java.lang.Class getRootType() {
        return Class.class;
    }
}

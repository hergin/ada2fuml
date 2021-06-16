package model.properties;

import model.Class;
import model.CustomPrimitive;
import model.Enumeration;
import model.AbstractProperty;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;

public class ClassProperty extends AbstractProperty implements IPlaceholderedElement {

    private Class type;

    private String placeholder;

    public ClassProperty(String name) {
        super(name, VisibilityEnum.Public);
        this.placeholder = "placeholdered"; // TODO this placeholdering should be taken care of!
    }

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

    public void changeToEnumerationProperty(Enumeration enumeration) {
        HierarchicalElement parent = getParent();
        if (parent instanceof Enumeration) {
            Enumeration castedParent = ((Enumeration) parent);
            castedParent.addProperty(new EnumerationProperty(getName(), enumeration));
            castedParent.getProperties().remove(this);
        } else if (parent instanceof Class) {
            Class castedParent = ((Class) parent);
            castedParent.addProperty(new EnumerationProperty(getName(), enumeration));
            castedParent.getProperties().remove(this);
        }
    }

    public void changeToCustomPrimitiveProperty(CustomPrimitive customPrimitive) {
        HierarchicalElement parent = getParent();
        if (parent instanceof Enumeration) {
            Enumeration castedParent = ((Enumeration) parent);
            castedParent.addProperty(new CustomPrimitiveProperty(getName(), getVisibility(), customPrimitive));
            castedParent.getProperties().remove(this);
        } else if (parent instanceof Class) {
            Class castedParent = ((Class) parent);
            castedParent.addProperty(new CustomPrimitiveProperty(getName(), getVisibility(), customPrimitive));
            castedParent.getProperties().remove(this);
        }
    }
}

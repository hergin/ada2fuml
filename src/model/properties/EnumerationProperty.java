package model.properties;

import model.*;
import model.Class;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;

public class EnumerationProperty extends AbstractProperty implements IPlaceholderedElement {

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

    public void fixType(Enumeration theEnum) {
        this.type = theEnum;
        this.placeholder = "";
    }

    @Override
    public void fixType(HierarchicalElement theReplacement) {
        fixType(((Enumeration) theReplacement));
    }

    @Override
    public java.lang.Class getRootType() {
        return Enumeration.class;
    }

    public Enumeration getType() {
        return type;
    }

    public void changeToClassProperty(Class type) {
        HierarchicalElement parent = getParent();
        if(parent instanceof Enumeration) {
            Enumeration castedParent = ((Enumeration) parent);
            castedParent.addProperty(new ClassProperty(getName(),getVisibility(), type));
            castedParent.getProperties().remove(this);
        } else if(parent instanceof Class) {
            Class castedParent = ((Class) parent);
            castedParent.addProperty(new ClassProperty(getName(),getVisibility(), type));
            castedParent.getProperties().remove(this);
        }
    }

    public void changeToCustomPrimitiveProperty(CustomPrimitive type) {
        HierarchicalElement parent = getParent();
        if(parent instanceof Enumeration) {
            Enumeration castedParent = ((Enumeration) parent);
            castedParent.addProperty(new CustomPrimitiveProperty(getName(),getVisibility(), type));
            castedParent.getProperties().remove(this);
        } else if(parent instanceof Class) {
            Class castedParent = ((Class) parent);
            castedParent.addProperty(new CustomPrimitiveProperty(getName(),getVisibility(), type));
            castedParent.getProperties().remove(this);
        }
    }
}

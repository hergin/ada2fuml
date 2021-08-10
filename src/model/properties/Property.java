package model.properties;

import model.AbstractProperty;
import model.Class;
import model.Enumeration;
import model.auxiliary.HierarchicalElement;
import model.enums.TypeEnum;
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

    public void convertToPrimitiveProperty(TypeEnum type) {
        HierarchicalElement parent = getParent();
        if (parent instanceof Enumeration) {
            Enumeration castedParent = ((Enumeration) parent);
            castedParent.addProperty(new PrimitiveProperty(getName(), VisibilityEnum.Public, type,value));
            castedParent.getProperties().remove(this);
        } else if (parent instanceof Class) {
            Class castedParent = ((Class) parent);
            castedParent.addProperty(new PrimitiveProperty(getName(), VisibilityEnum.Public, type,value));
            castedParent.getProperties().remove(this);
        }
    }

    public void convertToClassProperty(Class type) {
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

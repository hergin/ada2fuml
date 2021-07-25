package model.properties;

import model.*;
import model.Class;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;

public class StructProperty extends AbstractProperty implements IPlaceholderedElement {

    private Struct type;
    private String placeholder;

    public StructProperty(String name, Struct type) {
        super(name, VisibilityEnum.Public);
        this.type = type;
    }

    public StructProperty(String name, String placeholder) {
        super(name, VisibilityEnum.Public);
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void fixType(Struct theStruct) {
        this.type = theStruct;
        this.placeholder = "";
    }

    @Override
    public void fixType(HierarchicalElement theReplacement) {
        fixType(((Struct) theReplacement));
    }

    @Override
    public java.lang.Class getRootType() {
        return Struct.class;
    }

    public Struct getType() {
        return type;
    }

    public void changeToClassProperty(Class type) {
        HierarchicalElement parent = getParent();
        if(parent instanceof Struct) {
            Struct castedParent = ((Struct) parent);
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
        if(parent instanceof Struct) {
            Struct castedParent = ((Struct) parent);
            castedParent.addProperty(new CustomPrimitiveProperty(getName(),getVisibility(), type));
            castedParent.getProperties().remove(this);
        } else if(parent instanceof Class) {
            Class castedParent = ((Class) parent);
            castedParent.addProperty(new CustomPrimitiveProperty(getName(),getVisibility(), type));
            castedParent.getProperties().remove(this);
        }
    }
}

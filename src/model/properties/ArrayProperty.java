package model.properties;

import model.*;
// import model.Class;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;

public class ArrayProperty extends AbstractProperty implements IPlaceholderedElement {

    private Array type;
    private String placeholder;
    // No need to keep track of the elementType because it is embedded in Array type
    // private PrimitiveProperty elementType;

    public ArrayProperty(String name, Array type) {
        super(name, VisibilityEnum.Public);
        this.type = type;
    }

    public ArrayProperty(String name, String placeholder) {
        super(name, VisibilityEnum.Public);
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void fixType(Array theEnum) {
        this.type = theEnum;
        this.placeholder = "";
    }

    @Override
    public void fixType(HierarchicalElement theReplacement) {
        fixType(((Array) theReplacement));
    }

    @Override
    public java.lang.Class getRootType() {
        return Array.class;
    }

    public Array getType() {
        return type;
    }

//    public void changeToClassProperty(Class type) {
//        HierarchicalElement parent = getParent();
//        if(parent instanceof Array) {
//            Array castedParent = ((Array) parent);
//            castedParent.addProperty(new ClassProperty(getName(),getVisibility(), type));
//            castedParent.getProperties().remove(this);
//        } else if(parent instanceof Class) {
//            Class castedParent = ((Class) parent);
//            castedParent.addProperty(new ClassProperty(getName(),getVisibility(), type));
//            castedParent.getProperties().remove(this);
//        }
//    }
//
//    public void changeToCustomPrimitiveProperty(CustomPrimitive type) {
//        HierarchicalElement parent = getParent();
//        if(parent instanceof Array) {
//            Array castedParent = ((Array) parent);
//            castedParent.addProperty(new CustomPrimitiveProperty(getName(),getVisibility(), type));
//            castedParent.getProperties().remove(this);
//        } else if(parent instanceof Class) {
//            Class castedParent = ((Class) parent);
//            castedParent.addProperty(new CustomPrimitiveProperty(getName(),getVisibility(), type));
//            castedParent.getProperties().remove(this);
//        }
//    }
}

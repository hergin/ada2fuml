package model.parameters;

import model.*;
import model.Class;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.DirectionEnum;

public class EnumerationParameter extends AbstractParameter implements IPlaceholderedElement {

    private Enumeration type;
    private EnumerationLiteral defaultValue;
    private String placeholder;

    public EnumerationParameter(String name, DirectionEnum direction, Enumeration type) {
        super(name, direction);
        this.type = type;
    }

    public EnumerationParameter(String name, DirectionEnum direction, String placeholder) {
        super(name, direction);
        this.placeholder = placeholder;
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

    public void changeToClassParameter(Class type) {
        HierarchicalElement parent = getParent();
        Operation castedParent = ((Operation) parent);
        castedParent.addParameter(new ClassParameter(getName(),getDirection(),type));
        castedParent.getParameters().remove(this);
    }

    public void changeToCustomPrimitiveParameter(CustomPrimitive customPrimitive) {
        HierarchicalElement parent = getParent();
        Operation castedParent = ((Operation) parent);
        castedParent.addParameter(new CustomPrimitiveParameter(getName(),getDirection(),customPrimitive));
        castedParent.getParameters().remove(this);
    }
}

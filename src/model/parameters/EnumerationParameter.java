package model.parameters;

import model.EnumerationLiteral;
import model.Operation;
import model.Class;
import model.Parameter;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.DirectionEnum;
import model.Enumeration;

public class EnumerationParameter extends Parameter implements IPlaceholderedElement {

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
}

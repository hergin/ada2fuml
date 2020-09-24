package model.parameters;

import model.EnumerationLiteral;
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
    public Class getRootType() {
        return Enumeration.class;
    }

    public Enumeration getType() {
        return type;
    }
}

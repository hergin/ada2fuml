package model.parameters;

import model.EnumerationLiteral;
import model.Parameter;
import model.enums.DirectionEnum;
import model.Enumeration;

public class EnumerationParameter extends Parameter {

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

    public Enumeration getType() {
        return type;
    }
}

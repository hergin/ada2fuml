package model.parameters;

import model.AbstractParameter;
import model.enums.DirectionEnum;

public class Parameter extends AbstractParameter {

    private String reference;

    public Parameter() {
        this("", DirectionEnum.In);
    }

    public Parameter(String name, DirectionEnum direction) {
        super(name, direction);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

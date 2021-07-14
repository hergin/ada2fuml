package model.parameters;

import model.AbstractParameter;
import model.Class;
import model.Enumeration;
import model.Operation;
import model.auxiliary.HierarchicalElement;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.properties.PrimitiveProperty;

public class Parameter extends AbstractParameter {

    private String reference;

    public Parameter() {
        this("", DirectionEnum.In);
    }

    public Parameter(String name, DirectionEnum direction) {
        super(name, direction);
    }

    public void convertToPrimitiveParameter(TypeEnum type) {
        HierarchicalElement parent = getParent();
        Operation castedParent = ((Operation) parent);
        castedParent.addParameter(new PrimitiveParameter(getName(),getDirection(), type));
        castedParent.getParameters().remove(this);
    }

    public void convertToClassParameter(Class type) {
        HierarchicalElement parent = getParent();
        Operation castedParent = ((Operation) parent);
        castedParent.addParameter(new ClassParameter(getName(),getDirection(),type));
        castedParent.getParameters().remove(this);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

package model.properties;

import exporter.Processor;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;


public class Variable extends PrimitiveProperty {

    private String id;
    private boolean isConstantValue;

    public Variable (String name, VisibilityEnum visibility, TypeEnum type, Object defaultValue, boolean isConstant) {
        super(name, visibility, type, defaultValue);
        id = Processor.uuidGenerator();
        this.isConstantValue = isConstant;
    }


    // The following is inherited from Property
    // public VisibilityEnum getVisibility()

    public String getId() {
        return id;
    }

    public boolean isConstant() {
        return isConstantValue;
    }
}

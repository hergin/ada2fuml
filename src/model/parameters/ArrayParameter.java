package model.parameters;

import model.AbstractParameter;
import model.auxiliary.HierarchicalElement;
import model.enums.DirectionEnum;
import model.Array;

public class ArrayParameter extends AbstractParameter {

    private Array type;
    private String lower;
    private String upper;

    public ArrayParameter (String name, DirectionEnum direction, Array type, String lower, String upper) {
        super(name, direction);
        this.type = type;
        this.lower = lower;
        this.upper = upper;
    }

    public void fixType(Array type) {
        this.type = type;
    }

    public Array getType() {
        return type;
    }

    public HierarchicalElement getSubElementType() {
        return type.getSubElement();
    }

    public String getLower() {
    	return lower;
    }

    public String getUpper() {
    	return upper;
    }
}

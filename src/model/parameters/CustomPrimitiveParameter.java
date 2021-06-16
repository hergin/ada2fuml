package model.parameters;

import model.CustomPrimitive;
import model.AbstractParameter;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.DirectionEnum;

public class CustomPrimitiveParameter extends AbstractParameter implements IPlaceholderedElement {

    private CustomPrimitive type;
    private String placeholder;

    public CustomPrimitiveParameter(String name, DirectionEnum direction, String placeholder) {
        super(name, direction);
        this.placeholder = placeholder;
    }

    public CustomPrimitiveParameter(String name, DirectionEnum direction, CustomPrimitive type) {
        super(name, direction);
        this.type = type;
    }

    public CustomPrimitive getType() {
        return type;
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    public void fixType(CustomPrimitive theType) {
        this.type = theType;
        this.placeholder = "";
    }

    @Override
    public void fixType(HierarchicalElement theReplacement) {
        fixType(((CustomPrimitive) theReplacement));
    }

    @Override
    public Class getRootType() {
        return CustomPrimitive.class;
    }
}

package model.properties;

import model.CustomPrimitive;
import model.AbstractProperty;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;

public class CustomPrimitiveProperty extends AbstractProperty implements IPlaceholderedElement {

    private CustomPrimitive type;
    private String placeholder;

    public CustomPrimitiveProperty(String name, VisibilityEnum visibility, String placeholder) {
        super(name, visibility);
        this.placeholder = placeholder;
    }

    public CustomPrimitiveProperty(String name, VisibilityEnum visibility, CustomPrimitive type) {
        super(name, visibility);
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

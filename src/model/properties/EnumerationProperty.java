package model.properties;

import model.Enumeration;
import model.EnumerationLiteral;
import model.Property;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;

public class EnumerationProperty extends Property implements IPlaceholderedElement {

    private Enumeration type;
    private EnumerationLiteral defaultValue;
    private String placeholder;

    public EnumerationProperty(String name, Enumeration type) {
        super(name, VisibilityEnum.Public);
        this.type = type;
    }

    public EnumerationProperty(String name, String placeholder) {
        super(name, VisibilityEnum.Public);
        this.placeholder = placeholder;
    }

    public EnumerationProperty(String name, String placeholder, EnumerationLiteral defaultValue) {
        super(name, VisibilityEnum.Public);
        this.placeholder = placeholder;
        this.defaultValue = defaultValue;
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

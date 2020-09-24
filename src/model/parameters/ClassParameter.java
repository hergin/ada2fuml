package model.parameters;

import model.Enumeration;
import model.Operation;
import model.Parameter;
import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.DirectionEnum;
import model.Class;

public class ClassParameter extends Parameter implements IPlaceholderedElement {

    private Class type;
    private String placeholder;

    public ClassParameter (String name, DirectionEnum direction, String placeholder) {
        super(name, direction);
        this.placeholder = placeholder;
    }

    public ClassParameter (String name, DirectionEnum direction, Class type) {
        super(name, direction);
        this.type = type;
    }

    public void fixType(Class type) {
        this.type = type;
        this.placeholder = "";
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void fixType(HierarchicalElement theReplacement) {
        fixType(((Class) theReplacement));
    }

    @Override
    public java.lang.Class getRootType() {
        return Class.class;
    }

    public Class getType() {
        return type;
    }

    public void changeToEnumerationParameter(Enumeration enumeration) {
        HierarchicalElement parent = getParent();
        Operation castedParent = ((Operation) parent);
        castedParent.addParameter(new EnumerationParameter(getName(),getDirection(), enumeration));
        castedParent.getParameters().remove(this);
    }
}

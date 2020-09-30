package model;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.enums.TypeEnum;

import java.util.ArrayList;
import java.util.List;

public class CustomPrimitive extends HierarchicalElement implements IPlaceholderReplacement {

    private TypeEnum superPrimitive = null;
    private List<CustomPrimitive> superCustomPrimitives;

    public CustomPrimitive(String name) {
        super(name);
        this.superCustomPrimitives = new ArrayList<>();
    }

    public CustomPrimitive(String name, TypeEnum aSuperPrimitive) {
        this(name);
        this.superPrimitive = aSuperPrimitive;
    }

    public void addSuperCustomPrimitive(CustomPrimitive aSuperCustomPrimitive) {
        aSuperCustomPrimitive.setParent(this);
        superCustomPrimitives.add(aSuperCustomPrimitive);
    }

    public TypeEnum getSuperPrimitive() {
        return superPrimitive;
    }

    public void setSuperPrimitive(TypeEnum superPrimitive) {
        this.superPrimitive = superPrimitive;
    }

    public List<CustomPrimitive> getSuperCustomPrimitives() {
        return superCustomPrimitives;
    }

    @Override
    public HierarchicalElement getRealTypeOfPlaceholder() {
        return this;
    }
}

package model;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;
// import model.properties.ClassProperty;

import java.util.ArrayList;
import java.util.List;

public class Enumeration extends HierarchicalElement implements IPlaceholderReplacement {

    private List<AbstractProperty> properties;
    private List<EnumerationLiteral> literals;
    private static final VisibilityEnum visibility = VisibilityEnum.Public;

    public Enumeration (String name) {
        super(name);
        properties = new ArrayList<>();
        literals = new ArrayList<>();
    }

    public boolean hasPlaceholders() {
        for (AbstractProperty property:properties) {
            if(property instanceof IPlaceholderedElement && ((IPlaceholderedElement) property).hasPlaceholder())
                return true;
        }

        return false;
    }

    public void addProperty(AbstractProperty aProperty) {
        aProperty.setParent(this);
        properties.add(aProperty);
    }

    public void addLiteral(EnumerationLiteral aLiteral) {
        aLiteral.setParent(this);
        literals.add(aLiteral);
    }

    public List<AbstractProperty> getProperties() {
        return properties;
    }

    public List<EnumerationLiteral> getLiterals() {
        return literals;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    @Override
    public HierarchicalElement getRealTypeOfPlaceholder() {
        return this;
    }

    public List<IPlaceholderedElement> getElementsWithPlaceholder() {
        List<IPlaceholderedElement> result = new ArrayList<>();

        for (AbstractProperty property:properties) {
            if(property instanceof IPlaceholderedElement)
                result.add(((IPlaceholderedElement) property));
        }

        return result;
    }
}

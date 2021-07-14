package model;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.VisibilityEnum;
import model.parameters.Parameter;
import model.properties.Property;

import java.util.ArrayList;
import java.util.List;

public class Class extends HierarchicalElement implements IPlaceholderReplacement {

    private List<AbstractProperty> properties;
    private List<Operation> operations;
    private List<Class> superClasses;
    private List<Class> nestedClasses;
    private VisibilityEnum visibility;

    public Class() {
        this("");
    }

    public Class(String name) {
        super(name);
        properties = new ArrayList<>();
        operations = new ArrayList<>();
        superClasses = new ArrayList<>();
        nestedClasses = new ArrayList<>();
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityEnum visibility) {
        this.visibility = visibility;
    }

    public List<HierarchicalElement> getElementsWithReferences() {
        List<HierarchicalElement> result = new ArrayList<>();

        for (AbstractProperty property:properties) {
            if(property instanceof Property && !((Property) property).getReference().isEmpty())
                result.add(((HierarchicalElement) property));
        }

        for (Operation operation:operations) {
            for (AbstractParameter parameter:operation.getParameters()) {
                if(parameter instanceof Parameter && !((Parameter) parameter).getReference().isEmpty())
                    result.add(((HierarchicalElement) parameter));
            }
        }

        return result;
    }

    public List<IPlaceholderedElement> getElementsWithPlaceholder() {
        List<IPlaceholderedElement> result = new ArrayList<>();

        for (AbstractProperty property:properties) {
            if(property instanceof IPlaceholderedElement)
                result.add(((IPlaceholderedElement) property));
        }

        for (Operation operation:operations) {
            for (AbstractParameter parameter:operation.getParameters()) {
                if (parameter instanceof IPlaceholderedElement)
                    result.add(((IPlaceholderedElement) parameter));
            }
        }

        return result;
    }

    public boolean hasPlaceholders() {
        for (AbstractProperty property:properties) {
            if(property instanceof IPlaceholderedElement && ((IPlaceholderedElement) property).hasPlaceholder())
                return true;
        }

        for (Operation operation:operations) {
            for (AbstractParameter parameter:operation.getParameters()) {
                if (parameter instanceof IPlaceholderedElement && ((IPlaceholderedElement) parameter).hasPlaceholder())
                    return true;
            }
        }

        for(Class classs:nestedClasses) {
            if(classs.hasPlaceholders())
                return true;
        }

        return false;
    }

    public void addProperty(AbstractProperty someProperty) {
        someProperty.setParent(this);
        properties.add(someProperty);
    }

    public void addOperation(Operation someOperation) {
        someOperation.setParent(this);
        operations.add(someOperation);
    }

    public void addSuperClass (Class someClass) {
        superClasses.add(someClass);
    }

    public void addNestedClass (Class someClass) {
        someClass.setParent(this);
        nestedClasses.add(someClass);
    }

    public List<AbstractProperty> getProperties() {
        return properties;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Class> getSuperClasses() {
        return superClasses;
    }

    public List<Class> getNestedClasses() {
        return nestedClasses;
    }

    @Override
    public HierarchicalElement getRealTypeOfPlaceholder() {
        return this;
    }
}

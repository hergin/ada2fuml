package model;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;
import model.parameters.ClassParameter;
import model.properties.ClassProperty;

import java.util.ArrayList;
import java.util.List;

public class Class extends HierarchicalElement {

    private List<Property> properties;
    private List<Operation> operations;
    private List<Class> superClasses;
    private List<Class> nestedClasses;

    public Class(String name) {
        super(name);
        properties = new ArrayList<>();
        operations = new ArrayList<>();
        superClasses = new ArrayList<>();
        nestedClasses = new ArrayList<>();
    }

    public List<HierarchicalElement> findElementsWithPlaceholder() {
        List<HierarchicalElement> result = new ArrayList<>();

        for (Property property:properties) {
            if(property instanceof ClassProperty && ((ClassProperty) property).getPlaceholder()!=null && !((ClassProperty) property).getPlaceholder().isEmpty())
                result.add(property);
        }

        for (Operation operation:operations) {
            for (Parameter parameter:operation.getParameters()) {
                if (parameter instanceof ClassParameter && ((ClassParameter) parameter).getPlaceholder() != null && !((ClassParameter) parameter).getPlaceholder().isEmpty())
                    result.add(parameter);
            }
        }

        return result;
    }

    public boolean hasPlaceholders() {
        for (Property property:properties) {
            if(property instanceof IPlaceholderedElement && ((IPlaceholderedElement) property).hasPlaceholder())
                return true;
        }

        for (Operation operation:operations) {
            for (Parameter parameter:operation.getParameters()) {
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

    public void addProperty(Property someProperty) {
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

    public List<Property> getProperties() {
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
}

package model;

import model.auxiliary.HierarchicalElement;
import model.parameters.ClassParameter;
import model.properties.ClassProperty;

import java.util.ArrayList;
import java.util.List;

public class Interface extends HierarchicalElement {

    private List<AbstractProperty> properties;
    private List<Operation> operations;
    private List<Except> exceptions;
    private List<Struct> structs;
    private List<Interface> superInterfaces;
    private List<Enumeration> enumerations;

    public Interface(String name) {
        super(name);
        properties = new ArrayList<>();
        operations = new ArrayList<>();
        structs = new ArrayList<>();
        exceptions = new ArrayList<>();
        superInterfaces = new ArrayList<>();
        enumerations = new ArrayList<>();
    }

    public List<HierarchicalElement> findElementsWithPlaceholder() {
        List<HierarchicalElement> result = new ArrayList<>();

        for (AbstractProperty property:properties) {
            if(property instanceof ClassProperty && ((ClassProperty) property).getPlaceholder()!=null && !((ClassProperty) property).getPlaceholder().isEmpty())
                result.add(property);
        }

        for (Operation operation:operations) {
            for (AbstractParameter parameter:operation.getParameters()) {
                if (parameter instanceof ClassParameter && ((ClassParameter) parameter).getPlaceholder() != null && !((ClassParameter) parameter).getPlaceholder().isEmpty())
                    result.add(parameter);
            }
        }

        return result;
    }

    public boolean hasPlaceholders() {
        for (AbstractProperty property:properties) {
            if(property instanceof ClassProperty && ((ClassProperty) property).getPlaceholder()!=null && !((ClassProperty) property).getPlaceholder().isEmpty())
                return true;
        }

        for (Operation operation:operations) {
            for (AbstractParameter parameter:operation.getParameters()) {
                if (parameter instanceof ClassParameter && ((ClassParameter) parameter).getPlaceholder() != null && !((ClassParameter) parameter).getPlaceholder().isEmpty())
                    return true;
            }
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

    public void addExcept(Except inputExcept) {
        inputExcept.setParent(this);
        exceptions.add(inputExcept);
    }

    public void addStruct(Struct inputStruct) {
        inputStruct.setParent(this);
        structs.add(inputStruct);
    }

    public void addEnumeration(Enumeration inputEnumeration) {
    	inputEnumeration.setParent(this);
        enumerations.add(inputEnumeration);
    }

    public void addSuper(Interface someInterface) {
    	superInterfaces.add(someInterface);
    }

    public List<AbstractProperty> getProperties() {
        return properties;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Except> getExceptions() {
        return exceptions;
    }

    public List<Struct> getStructs() {
        return structs;
    }

    public List<Interface> getSuperInterfaces() {
        return superInterfaces;
    }

    public Struct getStructByName(String structName) {
        return structs.stream().filter(c->c.getName().equals(structName)).findFirst().get();
    }

    public List<Enumeration> getEnumerations() {
        return enumerations;
    }

    public Enumeration getEnumByName(String enumName) {
        return enumerations.stream().filter(c->c.getName().equals(enumName)).findFirst().get();
    }

}

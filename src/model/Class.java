package model;

import exporter.Processor;
import model.parameters.ClassParameter;
import model.properties.ClassProperty;

import java.util.ArrayList;
import java.util.List;

public class Class {

    private List<Property> properties;
    private List<Operation> operations;
    private List<Class> superClasses;
    private List<Class> nestedClasses;
    private String name;
    private String id;

    public Class(String name) {
        id = Processor.uuidGenerator();
        this.name = name;
        properties = new ArrayList<>();
        operations = new ArrayList<>();
        superClasses = new ArrayList<>();
        nestedClasses = new ArrayList<>();
    }

    public boolean hasPlaceholders() {
        for (var property:properties) {
            if(property instanceof ClassProperty && ((ClassProperty) property).getPlaceholder()!=null && !((ClassProperty) property).getPlaceholder().isEmpty())
                return true;
        }

        for (var operation:operations) {
            for (var parameter:operation.getParameters()) {
                if (parameter instanceof ClassParameter && ((ClassParameter) parameter).getPlaceholder() != null && !((ClassParameter) parameter).getPlaceholder().isEmpty())
                    return true;
            }
        }

        for(var classs:nestedClasses) {
            if(classs.hasPlaceholders())
                return true;
        }



        return false;
    }

    public void addProperty(Property someProperty) {
        properties.add(someProperty);
    }

    public void addOperation(Operation someOperation) {
        operations.add(someOperation);
    }

    public void addSuperClass (Class someClass) {
        superClasses.add(someClass);
    }

    public void addNestedClass (Class someClass) {nestedClasses.add(someClass); }

    public List<Property> getProperties() {
        return properties;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Class> getSuperClasses() {
        return superClasses;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Class> getNestedClasses() {
        return nestedClasses;
    }
}

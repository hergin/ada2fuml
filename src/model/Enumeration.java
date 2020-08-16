package model;

import exporter.Processor;
import model.enums.VisibilityEnum;
// import model.properties.ClassProperty;
import model.properties.EnumerationProperty;

import java.util.ArrayList;
import java.util.List;

public class Enumeration extends HierarchicalElement {

    private List<Property> properties;
    private List<EnumerationLiteral> literals;
    private static final VisibilityEnum visibility = VisibilityEnum.Public;
    protected String id;

    public Enumeration (String name) {
        super(name);
        id = Processor.uuidGenerator();
        properties = new ArrayList<>();
        literals = new ArrayList<>();
    }

    public void addProperty(Property aProperty) {
        aProperty.setParent(this);
        properties.add(aProperty);
    }

    public void addLiteral(EnumerationLiteral aLiteral) {
        aLiteral.setParent(this);
        literals.add(aLiteral);
    }

    public String getId() {
        return id;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<EnumerationLiteral> getLiterals() {
        return literals;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

}

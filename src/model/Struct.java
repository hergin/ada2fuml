package model;

import exporter.Processor;
import model.enums.VisibilityEnum;

import java.util.ArrayList;
import java.util.List;

public class Struct extends HierarchicalElement {

    private List<Property> properties;
    private VisibilityEnum visibility;

    public Struct (String name) {
        super(name);
        properties = new ArrayList<>();
        this.visibility = VisibilityEnum.Public;
    }

    public void addProperty(Property someProperty) {
        someProperty.setParent(this);
        properties.add(someProperty);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

}

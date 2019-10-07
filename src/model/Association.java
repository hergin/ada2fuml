package model;

import exporter.Processor;
import model.properties.AssociationProperty;

import java.util.ArrayList;
import java.util.List;

public class Association extends HierarchicalElement {

    private String id;
    private Class source;
    private Class target;

    private List<AssociationProperty> properties;

    public Association(String name, Class source, Class target) {
        super(name);
        id = Processor.uuidGenerator();
        this.source = source;
        this.target = target;
        properties = new ArrayList<>();
    }

    public void addProperty(AssociationProperty property) {
        properties.add(property);
    }

    public List<AssociationProperty> getProperties() {
        return properties;
    }

    public Class getSource() {
        return source;
    }

    public Class getTarget() {
        return target;
    }

    public String getId() {
        return id;
    }
}

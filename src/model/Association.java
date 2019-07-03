package model;

import exporter.Processor;
import model.properties.AssociationProperty;

import java.util.ArrayList;
import java.util.List;

public class Association {

    private String id;
    private String name;
    private Class source;
    private Class target;

    private List<AssociationProperty> properties;

    public Association(String name, Class source, Class target) {
        id = Processor.uuidGenerator();
        this.name = name;
        this.source = source;
        this.target = target;
        properties = new ArrayList<>();
    }

    public void addProperty(AssociationProperty property) {
        properties.add(property);
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

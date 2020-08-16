package model;

import model.auxiliary.HierarchicalElement;
import model.properties.AssociationProperty;

import java.util.ArrayList;
import java.util.List;

public class Association extends HierarchicalElement {

    private Class source;
    private Class target;

    private List<AssociationProperty> properties;

    public Association(String name, Class source, Class target) {
        super(name);
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
}

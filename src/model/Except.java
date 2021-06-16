package model;

import model.auxiliary.HierarchicalElement;
import model.enums.VisibilityEnum;

import java.util.ArrayList;
import java.util.List;

public class Except extends HierarchicalElement {

    private List<AbstractProperty> properties;
    private VisibilityEnum visibility;

    public Except (String name) {
        super(name);
        properties = new ArrayList<>();
        this.visibility = VisibilityEnum.Public;
    }

    public void addProperty(AbstractProperty someProperty) {
        someProperty.setParent(this);
        properties.add(someProperty);
    }

    public List<AbstractProperty> getProperties() {
        return properties;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

}

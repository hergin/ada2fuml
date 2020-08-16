package model;

import exporter.Processor;
import model.enums.VisibilityEnum;

public abstract class Property extends HierarchicalElement {

    private VisibilityEnum visibility;

    public Property(String name, VisibilityEnum visibility) {
        super(name);
        this.visibility = visibility;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

}

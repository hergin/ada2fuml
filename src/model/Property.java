package model;

import exporter.Processor;
import model.enums.VisibilityEnum;

public abstract class Property extends HierarchicalElement {

    private VisibilityEnum visibility;
    private String id;

    public Property(String name, VisibilityEnum visibility) {
        super(name);
        id = Processor.uuidGenerator();
        this.visibility = visibility;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public String getId() {
        return id;
    }
}

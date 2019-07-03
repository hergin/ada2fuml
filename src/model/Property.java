package model;

import exporter.Processor;
import model.enums.VisibilityEnum;

public abstract class Property {

    private String name;
    private VisibilityEnum visibility;
    private String id;

    public Property(String name, VisibilityEnum visibility) {
        id = Processor.uuidGenerator();
        this.name = name;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public String getId() {
        return id;
    }
}

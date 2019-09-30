package model;

import exporter.Processor;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;

public abstract class Parameter extends HierarchicalElement {

    private DirectionEnum direction;
    private String id;

    public Parameter(String name, DirectionEnum direction) {
        super(name);
        this.direction = direction;
        id = Processor.uuidGenerator();
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public String getId() { return id; }
}

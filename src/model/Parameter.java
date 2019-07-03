package model;

import exporter.Processor;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;

public abstract class Parameter {

    private String name;
    private DirectionEnum direction;
    private String id;

    public Parameter(String name, DirectionEnum direction) {
        this.name = name;
        this.direction = direction;
        id = Processor.uuidGenerator();
    }

    public String getName() {
        return name;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public String getId() { return id; }
}

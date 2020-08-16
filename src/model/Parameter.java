package model;

import model.auxiliary.HierarchicalElement;
import model.enums.DirectionEnum;
// import model.enums.TypeEnum;

public abstract class Parameter extends HierarchicalElement {

    private DirectionEnum direction;

    public Parameter(String name, DirectionEnum direction) {
        super(name);
        this.direction = direction;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

}

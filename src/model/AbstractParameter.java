package model;

import model.auxiliary.HierarchicalElement;
import model.enums.DirectionEnum;
// import model.enums.TypeEnum;

public abstract class AbstractParameter extends HierarchicalElement {

    private DirectionEnum direction;

    public AbstractParameter(String name, DirectionEnum direction) {
        super(name);
        this.direction = direction;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }
}

package model;

import model.auxiliary.HierarchicalElement;
import model.enums.VisibilityEnum;

public abstract class AbstractProperty extends HierarchicalElement {

    private VisibilityEnum visibility;

    public AbstractProperty(String name, VisibilityEnum visibility) {
        super(name);
        this.visibility = visibility;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityEnum visibility) {
        this.visibility = visibility;
    }
}

package model;

import model.auxiliary.HierarchicalElement;
import model.enums.VisibilityEnum;

public abstract class AbstractProperty extends HierarchicalElement {

    private VisibilityEnum visibility;
    private String lower;
    private String upper;

    public AbstractProperty(String name, VisibilityEnum visibility) {
        super(name);
        this.visibility = visibility;
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityEnum visibility) {
        this.visibility = visibility;
    }
}

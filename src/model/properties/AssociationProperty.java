package model.properties;

import model.Association;
import model.AbstractProperty;
import model.enums.VisibilityEnum;

public class AssociationProperty extends AbstractProperty {

    private Association association;

    public AssociationProperty(String name, VisibilityEnum visibility, Association association) {
        super(name, visibility);
        this.association = association;
        this.association.addProperty(this);
    }

    public Association getAssociation() {
        return association;
    }
}

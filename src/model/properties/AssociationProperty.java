package model.properties;

import model.Association;
import model.Property;
import model.enums.VisibilityEnum;

public class AssociationProperty extends Property {

    private Association association;

    public AssociationProperty(String name, VisibilityEnum visibility, Association association) {
        super(name, visibility);
        this.association = association;
    }

    public Association getAssociation() {
        return association;
    }
}

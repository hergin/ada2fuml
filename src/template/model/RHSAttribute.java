package template.model;

public class RHSAttribute extends RHS {

    String name;

    public RHSAttribute(String attributeName) {
        this.name = attributeName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

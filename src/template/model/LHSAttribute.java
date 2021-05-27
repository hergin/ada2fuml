package template.model;

public class LHSAttribute extends LHS {

    String name;

    public LHSAttribute(String attributeName) {
        this.name = attributeName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "@" + name;
    }
}

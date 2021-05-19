package template.model;

public class RHSClass extends RHS {

    String name;

    public RHSClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

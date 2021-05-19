package template.model;

public class LHSAttribute extends LHS {

    String name;

    public LHSAttribute(String attributeName) {
        super("");
        this.name = attributeName;
    }

    public LHSAttribute(String path, String attributeName) {
        super(path);
        this.name = attributeName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return (path.isEmpty() ? "" : path + "/") + "@" + name;
    }
}

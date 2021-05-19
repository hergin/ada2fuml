package template.model;

public class RHSAttributeInClass extends RHS {

    String className;
    String attributeName;

    public RHSAttributeInClass(String className, String attributeName) {
        this.className = className;
        this.attributeName = attributeName;
    }

    public String getClassName() {
        return className;
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String toString() {
        return className + " in " + attributeName;
    }
}

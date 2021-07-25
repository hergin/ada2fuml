package template.model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RHSAttributeInClass that = (RHSAttributeInClass) o;
        return Objects.equals(className, that.className) && Objects.equals(attributeName, that.attributeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, attributeName);
    }

    @Override
    public String toString() {
        return className + " in " + attributeName;
    }
}

package template.model;

import java.util.Objects;

public class RHSAttributeInClass extends RHS {

    String className;
    String attributeName;
    String condition;

    public RHSAttributeInClass(String className, String attributeName) {
        this.className = className;
        this.attributeName = attributeName;
        this.condition = "";
    }

    public RHSAttributeInClass(String className, String attributeName, String condition) {
        this.className = className;
        this.attributeName = attributeName;
        this.condition = condition;
    }

    public String getClassName() {
        return className;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RHSAttributeInClass that = (RHSAttributeInClass) o;
        return Objects.equals(className, that.className) && Objects.equals(attributeName, that.attributeName)
                && Objects.equals(condition, that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, attributeName, condition);
    }

    @Override
    public String toString() {
        return className + " in " + attributeName + (condition.isEmpty() ? "" : " when(" + condition + ")");
    }
}

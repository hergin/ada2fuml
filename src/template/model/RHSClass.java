package template.model;

import java.util.Objects;

public class RHSClass extends RHS {

    String name;

    public RHSClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RHSClass rhsClass = (RHSClass) o;
        return Objects.equals(name, rhsClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

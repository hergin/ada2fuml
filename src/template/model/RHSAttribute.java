package template.model;

import java.util.Objects;

public class RHSAttribute extends RHS {

    String name;

    public RHSAttribute(String attributeName) {
        this.name = attributeName;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RHSAttribute that = (RHSAttribute) o;
        return Objects.equals(name, that.name);
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

package template.model;

public class LHSLiteral extends LHS {

    private String value;

    public LHSLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }
}

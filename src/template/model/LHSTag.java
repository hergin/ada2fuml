package template.model;

public class LHSTag extends LHS {

    String tag;

    public LHSTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }

    public String getTag() {
        return tag;
    }
}

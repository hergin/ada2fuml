package template.model;

public class LHSAttributeWithPath extends LHS {

    String path;
    String attributeName;

    public LHSAttributeWithPath(String attributeName, String path) {
        this.attributeName = attributeName;
        if (path.endsWith("/")) {
            this.path = path.substring(0, path.length() - 1);
        } else {
            this.path = path;
        }
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return (path.isEmpty() ? "" : path + "/") + "@" + attributeName;
    }
}

package template.model;

public abstract class LHS {

    String path;

    public LHS(String path) {
        if (path.endsWith("/")) {
            this.path = path.substring(0, path.length() - 1);
        } else {
            this.path = path;
        }
    }

    public String getPath() {
        return path;
    }
}

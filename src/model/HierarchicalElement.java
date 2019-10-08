package model;

public class HierarchicalElement {

    private String name;
    private HierarchicalElement parent;

    public HierarchicalElement(String name) {
        this.name = name;
    }

    public HierarchicalElement getParent() {
        return parent;
    }

    public void setParent(HierarchicalElement parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        StringBuilder sb = new StringBuilder(name);

        HierarchicalElement current = parent;

        while(current!=null && !(current instanceof UML)) {
            sb.insert(0,current.getName()+".");
            current = current.getParent();
        }

        return sb.toString();
    }
}

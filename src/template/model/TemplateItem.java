package template.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateItem {

    List<TemplateItem> subItems;
    LHS lhs;
    RHS rhs;

    public TemplateItem(LHS lhs, RHS rhs) {
        subItems = new ArrayList<>();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void addSubItem(TemplateItem subItem) {
        this.subItems.add(subItem);
    }

    public List<TemplateItem> getSubItems() {
        return subItems;
    }

    public LHS getLhs() {
        return lhs;
    }

    public RHS getRhs() {
        return rhs;
    }

    public List<String> getLines() {
        List<String> subs = new ArrayList<>();
        subs.add(lhs.toString() + " -- " + rhs.toString());
        for (TemplateItem subItem : subItems) {
            subs.addAll(subItem.getLines().stream().map(l -> ">" + l).collect(Collectors.toList()));
        }
        return subs;
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), getLines());
    }
}

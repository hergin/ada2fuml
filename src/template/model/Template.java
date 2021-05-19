package template.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Template {

    List<TemplateItem> items;

    public Template() {
        items = new ArrayList<>();
    }

    public void addItem(TemplateItem item) {
        items.add(item);
    }

    public List<TemplateItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), items.stream().map(i -> i.toString()).collect(Collectors.joining()));
    }
}

package exceptions;

import model.HierarchicalElement;

import java.util.ArrayList;
import java.util.List;

public class StillHavePlaceHolderException extends Exception {

    List<HierarchicalElement> items;

    public StillHavePlaceHolderException(String message, List<HierarchicalElement> items) {
        super(message);
        this.items = new ArrayList<>(items);
    }

    public List<HierarchicalElement> getItems() {
        return items;
    }
}

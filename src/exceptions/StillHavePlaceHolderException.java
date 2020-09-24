package exceptions;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderedElement;

import java.util.ArrayList;
import java.util.List;

public class StillHavePlaceHolderException extends Exception {

    List<IPlaceholderedElement> items;

    public StillHavePlaceHolderException(String message, List<IPlaceholderedElement> items) {
        super(message);
        this.items = new ArrayList<IPlaceholderedElement>(items);
    }

    public List<IPlaceholderedElement> getItems() {
        return items;
    }
}

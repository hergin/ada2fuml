package model.auxiliary;

public interface IPlaceholderedElement {
    String getPlaceholder();
    default Boolean hasPlaceholder() {
        return getPlaceholder()!=null && !getPlaceholder().isEmpty();
    }
}

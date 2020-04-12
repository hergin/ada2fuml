package model;

import exporter.Processor;
import model.enums.VisibilityEnum;
// import model.properties.ClassProperty;
import model.properties.EnumerationProperty;

import java.util.ArrayList;
import java.util.List;

public class Enumeration extends HierarchicalElement {

    private List<EnumerationProperty> properties;
    private static final VisibilityEnum visibility = VisibilityEnum.Public;
    private String id;

    public Enumeration (String name) {
        super(name);
        properties = new ArrayList<>();
        id = Processor.uuidGenerator();
    }

//    public List<HierarchicalElement> findElementsWithPlaceholder() {
//        List<HierarchicalElement> result = new ArrayList<>();
//
//        for (Property property:properties) {
//            if(property instanceof ClassProperty && ((ClassProperty) property).getPlaceholder()!=null && !((ClassProperty) property).getPlaceholder().isEmpty())
//                result.add(property);
//        }
//
//        return result;
//    }
//
//    public boolean hasPlaceholders() {
//        for (Property property:properties) {
//            if(property instanceof ClassProperty && ((ClassProperty) property).getPlaceholder()!=null && !((ClassProperty) property).getPlaceholder().isEmpty())
//                return true;
//        }
//
//        return false;
//    }

    public void addProperty(EnumerationProperty someProperty) {
        someProperty.setParent(this);
        properties.add(someProperty);
    }

    public List<EnumerationProperty> getProperties() {
        return properties;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public String getId() {
        return id;
    }
}

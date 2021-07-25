package model;

import model.auxiliary.HierarchicalElement;
import model.enums.VisibilityEnum;
import model.properties.PrimitiveProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Array extends HierarchicalElement {

	static final String lower = "lowerValue";
	static final String upper = "upperValue";
    Map<String, AbstractProperty> properties;
    private VisibilityEnum visibility;
    private HierarchicalElement subElementType;

    public Array (String name, HierarchicalElement elementType) {
        super(name);
        properties = new LinkedHashMap<String, AbstractProperty>();
        this.visibility = VisibilityEnum.Public;
        this.subElementType = elementType;
    }

    public HierarchicalElement getSubElement () {
    	return subElementType;
    }

    public void addProperty(AbstractProperty someProperty) {
    	String name = someProperty.getName();
        someProperty.setParent(this);
        properties.put(name, someProperty);
    }

    public String getLower () {
    	if ( properties.containsKey(lower) ) {
    		PrimitiveProperty p = (PrimitiveProperty) properties.get(lower);
    		String result = p.getDefaultValue().toString();
    		return result;
    	}

    	return null;
    }

    public String getUpper() {
    	if ( properties.containsKey(upper) ) {
    		PrimitiveProperty p = (PrimitiveProperty) properties.get(upper);
    		String result = p.getDefaultValue().toString();
    		return result;
    	}

    	return null;
    }

//    public List<Property> getProperties() {
//        return properties;
//    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

}

package template;

import extractor.Extractor;
import model.Class;
import model.Package;
import model.Property;
import model.UML;
import model.auxiliary.HierarchicalElement;
import model.enums.VisibilityEnum;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import template.model.*;
import utils.XMLUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemplateInterpreter {

    public static UML interpret(Document xmlDocument, Template template) {
        UML uml = new UML("overall");
        for (TemplateItem item : template.getItems()) {
            if (item.getLhs() instanceof LHSTag) {
                NodeList nodes = XMLUtils.getAllNodesWithThePath(xmlDocument, item.getLhs().getPath());
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node currentNode = nodes.item(i);
                    if (item.getRhs() instanceof RHSClass) {
                        HierarchicalElement element = createTheElement(((RHSClass) item.getRhs()).getName());
                        processFurther(currentNode, element, item.getSubItems());
                        if (element instanceof UML) {
                            uml.combine(((UML) element));
                        } else if (element instanceof Package) {
                            uml.addPackage(((Package) element));
                        }
                    }
                }
            }
        }
        return uml;
    }

    private static void processFurther(Node parentNode, HierarchicalElement parentElement, List<TemplateItem> subItems) {
        for (TemplateItem item : subItems) {
            if (item.getLhs() instanceof LHSTag) {
                NodeList nodes = XMLUtils.getAllNodesWithThePath(parentNode, item.getLhs().getPath());
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node currentNode = nodes.item(i);
                    if (item.getRhs() instanceof RHSAttributeInClass) {
                        HierarchicalElement element = createTheElement(((RHSAttributeInClass) item.getRhs()).getClassName());
                        addElementToTheList(element, parentElement, ((RHSAttributeInClass) item.getRhs()).getAttributeName());
                        processFurther(currentNode, element, item.getSubItems());
                    }
                }
            } else if (item.getLhs() instanceof LHSAttribute) {
                if (((LHSAttribute) item.getLhs()).getPath().isEmpty()) {
                    String value = getAttributeValueOfNode(parentNode, ((LHSAttribute) item.getLhs()).getName());
                    if (item.getRhs() instanceof RHSAttribute) {
                        setAttributeValueOfParentElement(parentElement, ((RHSAttribute) item.getRhs()).getName(), value);
                    }
                } else {
                    Node currentNode = XMLUtils.getAllNodesWithThePath(parentNode, item.getLhs().getPath()).item(0);
                    // TODO this assumes we will only have 1 sub-nodes. Should be researched more.
                    String value = getAttributeValueOfNode(currentNode, ((LHSAttribute) item.getLhs()).getName());
                    if (item.getRhs() instanceof RHSAttribute) {
                        setAttributeValueOfParentElement(parentElement, ((RHSAttribute) item.getRhs()).getName(), value);
                    }
                }
            }
        }
    }

    private static String getAttributeValueOfNode(Node parentNode, String attributeName) {
        if (attributeName.equals("value")) {
            return parentNode.getNodeValue();
        } else {
            return parentNode.getAttributes().getNamedItem(attributeName).getNodeValue();
        }
    }

    private static void setAttributeValueOfParentElement(HierarchicalElement parentElement, String nameOfTheAttribute, String valueOfTheAttribute) {
        try {
            getField(parentElement.getClass(), nameOfTheAttribute).set(parentElement, valueOfTheAttribute);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // TODO add element to the attribute of the parentElement by REFLECTION
    private static void addElementToTheList(HierarchicalElement element, HierarchicalElement parentElement, String attributeName) {
        if (parentElement instanceof UML) {
            if (attributeName.equals("classes")) {
                if (element instanceof Class) {
                    ((UML) parentElement).addClass(((Class) element));
                }
            } else if (attributeName.equals("packages")) {
                if (element instanceof Package) {
                    ((UML) parentElement).addPackage(((Package) element));
                }
            }
        } else if (parentElement instanceof Package) {
            if (attributeName.equals("classes")) {
                if (element instanceof Class) {
                    ((Package) parentElement).addClass(((Class) element));
                }
            }
        } else if (parentElement instanceof Class) {
            if (attributeName.equals("properties")) {
                if (element instanceof Property) {
                    ((Class) parentElement).addProperty(((Property) element));
                }
            }
        }

    }

    public static Field getField(java.lang.Class<?> type, String fieldName) {
        for (java.lang.Class<?> c = type; c != null; c = c.getSuperclass()) {
            for (Field field : Arrays.asList(c.getDeclaredFields())) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    return field;
                }
            }
        }
        return null;
    }

    // TODO create element to the attribute of the parentElement by REFLECTION
    private static HierarchicalElement createTheElement(String name) {
        switch (name) {
            case "UML":
                return new UML("");
            case "Class":
                return new Class("");
            case "Package":
                return new Package("");
            case "ClassProperty":
                return new ClassProperty("",VisibilityEnum.Public,"SomePlaceholder"); // TODO this placeholdering should change!
            default:
                if (name.startsWith("PrimitiveProperty")) {
                    return new PrimitiveProperty("", VisibilityEnum.Public, Extractor.convertToTypeEnum(name.substring(18, name.length() - 1)), null);
                }
        }
        return null;
    }

}

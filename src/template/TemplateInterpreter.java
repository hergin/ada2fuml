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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemplateInterpreter {

    public static UML interpret(Document xmlDocument, Template template) {
        UML uml = new UML("overall");
        for (TemplateItem item : template.getItems()) {
            if (item.getLhs() instanceof LHSTag) {
                NodeList nodes = XMLUtils.getAllNodesWithThePath(xmlDocument, ((LHSTag) item.getLhs()).getTag());
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
                NodeList nodes = XMLUtils.getAllNodesWithThePath(parentNode, ((LHSTag) item.getLhs()).getTag());
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node currentNode = nodes.item(i);
                    if (item.getRhs() instanceof RHSAttributeInClass) {
                        HierarchicalElement element = createTheElement(((RHSAttributeInClass) item.getRhs()).getClassName());
                        addElementToTheList(element, parentElement, ((RHSAttributeInClass) item.getRhs()).getAttributeName());
                        processFurther(currentNode, element, item.getSubItems());
                    }
                }
            } else if (item.getLhs() instanceof LHSAttribute) {
                String value = getAttributeValueOfNode(parentNode, ((LHSAttribute) item.getLhs()).getName());
                if (item.getRhs() instanceof RHSAttribute) {
                    setAttributeValueOfParentElement(parentElement, ((RHSAttribute) item.getRhs()).getName(), value);
                }
            } else if (item.getLhs() instanceof LHSAttributeWithPath) {
                Node currentNode = XMLUtils.getAllNodesWithThePath(parentNode, ((LHSAttributeWithPath) item.getLhs()).getPath()).item(0);
                // TODO this assumes we will only have 1 sub-nodes. Should be researched more.
                String value = getAttributeValueOfNode(currentNode, ((LHSAttributeWithPath) item.getLhs()).getAttributeName());
                if (item.getRhs() instanceof RHSAttribute) {
                    setAttributeValueOfParentElement(parentElement, ((RHSAttribute) item.getRhs()).getName(), value);
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
        Method addMethod = getMethod(parentElement.getClass(), "add" + element.getClass().getSimpleName());
        if (addMethod == null) {
            addMethod = getMethod(parentElement.getClass(), "add" + element.getClass().getSuperclass().getSimpleName()); // TODO find more elegant solution. This is for PrimitiveProperty or any subclass or property. They don't have addPrimitiveProperty method, so we have to find addProperty instead.
        }
        try {
            addMethod.invoke(parentElement, element);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
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

    public static Method getMethod(java.lang.Class<?> type, String methodName) {
        for (java.lang.Class<?> c = type; c != null; c = c.getSuperclass()) {
            for (Method method : Arrays.asList(c.getDeclaredMethods())) {
                if (method.getName().equals(methodName)) {
                    method.setAccessible(true);
                    return method;
                }
            }
        }
        return null;
    }

    // TODO create element to the attribute of the parentElement by REFLECTION
    private static HierarchicalElement createTheElement(String name) {
        if (name.startsWith("PrimitiveProperty")) {
            return new PrimitiveProperty("", VisibilityEnum.Public, Extractor.convertToTypeEnum(name.substring(18, name.length() - 1)), null);
        } else {
            try {
                return (HierarchicalElement) java.lang.Class.forName("model." + name).getConstructor(String.class).newInstance("");
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                try {
                    return (HierarchicalElement) java.lang.Class.forName("model.properties." + name).getConstructor(String.class).newInstance("");
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException inner) {
                    inner.printStackTrace();
                }
            }
        }
        return null;
    }

}

package model;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.auxiliary.IPlaceholderedElement;
import model.enums.PlaceholderPreferenceEnum;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.parameters.ClassParameter;
import model.parameters.EnumerationParameter;
import model.properties.AssociationProperty;
import model.properties.ClassProperty;
import model.properties.EnumerationProperty;
import template.TemplateInterpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class UML extends HierarchicalElement {

    private String fileName;
    private List<Class> classes;
    private List<Package> packages;
    private List<Interface> interfaces;
    private List<Enumeration> enumerations;
    private List<Association> associations;
    private List<CustomPrimitive> customPrimitives;

    public UML() {
        this("");
    }

    public UML(String name) {
        super(name);
        classes = new ArrayList<>();
        packages = new ArrayList<>();
        interfaces = new ArrayList<>();
        enumerations = new ArrayList<>();
        associations = new ArrayList<>();
        customPrimitives = new ArrayList<>();
    }

    public UML(String name, String fileName) {
        this(name);
        this.fileName = fileName;
    }

    public void addInterface(Interface interfacee) {
        interfacee.setParent(this);
        this.interfaces.add(interfacee);
    }

    public List<IPlaceholderedElement> getItemsWithPlaceholders() {
        List<Class> allClasses = collectAllClasses();
        List<Class> classesThatHavePlaceholders = allClasses.stream().filter(c->c.hasPlaceholders()).collect(Collectors.toList());

        List<IPlaceholderedElement> result = new ArrayList<IPlaceholderedElement>();
        classesThatHavePlaceholders.stream().forEach(c->result.addAll(c.getElementsWithPlaceholder()));

        return result;
    }

    public void combine(UML otherUML) {
        otherUML.getClasses().forEach(c->addClass(c));
        otherUML.getEnumerations().forEach(e->addEnumeration(e));
        otherUML.getCustomPrimitives().forEach(cp->addCustomPrimitive(cp));
        otherUML.getInterfaces().forEach(i->addInterface(i));

        for(Package otherPackage:otherUML.getPackages()) {
            List<Package> sameNamedPackages = packages.stream().filter(p->p.getName().equals(otherPackage.getName())).collect(Collectors.toList());
            if(sameNamedPackages.size()>0) {
                Package sameNamedPackage = sameNamedPackages.get(0);
                otherPackage.getClasses().forEach(c->sameNamedPackage.addClass(c));
                otherPackage.getEnumerations().forEach(e->sameNamedPackage.addEnumeration(e));
                otherPackage.getSubPackages().forEach(sb->sameNamedPackage.addSubPackage(sb));
                otherPackage.getCustomPrimitives().forEach(cp->sameNamedPackage.addCustomPrimitive(cp));
            } else {
                addPackage(otherPackage);
            }
        }

        otherUML.getAssociations().forEach(a->addAssociation(a));
    }

    public boolean hasPlaceholders() {

        for(Enumeration anEnum:enumerations) {
            if(anEnum.hasPlaceholders())
                return true;
        }

        for(Class aClass:classes) {
            if(aClass.hasPlaceholders())
                return true;
        }

        for(Package aPackage:packages) {
            if(aPackage.hasPlaceholders())
                return true;
        }

        return false;
    }

    public void replaceReferences() {
        List<HierarchicalElement> allElementsWithReferences = collectAllElementsWithReferences();

        for(HierarchicalElement element:allElementsWithReferences) {
            replaceReference(element);
        }
    }

    private void replaceReference(HierarchicalElement element) {
        try {
            String reference = (String) TemplateInterpreter.getMethod(element.getClass(),"getReference").invoke(element);

            // Try to fix primitives first
            for(TypeEnum primitive:TypeEnum.values()) {
                if (reference.toLowerCase().contains(primitive.toString().toLowerCase())) {
                    TemplateInterpreter.getMethod(element.getClass(), "convertToPrimitive" + element.getClass().getSimpleName()).invoke(element, primitive);
                    return;
                }
            }

            // Try shorthands for primitives
            Map<String, TypeEnum> shortHands = new HashMap<>();
            shortHands.put("int",TypeEnum.Integer);
            for(Map.Entry<String,TypeEnum> entry : shortHands.entrySet()) {
                if(entry.getKey().toLowerCase().equals(reference.toLowerCase())) {
                    TemplateInterpreter.getMethod(element.getClass(), "convertToPrimitive" + element.getClass().getSimpleName()).invoke(element, entry.getValue());
                }
            }

            // Try to fix classes
            List<Class> allClasses = collectAllClasses();
            Class longestMatchingClass = new Class("");
            for(Class aClass:allClasses) {
                if(reference.contains(aClass.getName()) && aClass.getName().length()>longestMatchingClass.getName().length()) {
                    longestMatchingClass = aClass;
                }
            }
            if(longestMatchingClass.getName().length()>0)
                TemplateInterpreter.getMethod(element.getClass(), "convertToClass" + element.getClass().getSimpleName()).invoke(element, longestMatchingClass);

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private List<HierarchicalElement> collectAllElementsWithReferences() {
        List<HierarchicalElement> result = new ArrayList<>();

        for (Class aClass:classes) {
            result.addAll(aClass.getElementsWithReferences());
        }

        for(Package aPackage:packages) {
            result.addAll(Package.getAllElementsWithReferencesRecursively(aPackage));
        }

        return result;
    }

    public void fixPlaceholders(PlaceholderPreferenceEnum preference) {

        List<IPlaceholderReplacement> allPlaceholderReplacers = collectAllPlaceholderReplacements();
        List<IPlaceholderedElement> allElementsWithPlaceholdersPossibly = collectAllPlaceholderedElements();
        List<IPlaceholderedElement> allElementsWithPlaceholders = allElementsWithPlaceholdersPossibly.stream().filter(c->c.hasPlaceholder()).collect(Collectors.toList());

        for (IPlaceholderedElement anElementWithPlaceholder:allElementsWithPlaceholders) {
            String placeholder = anElementWithPlaceholder.getPlaceholder();
            HierarchicalElement replacedElement = null;

            for(IPlaceholderReplacement aPlaceholderReplacer:allPlaceholderReplacers) {
                HierarchicalElement theReplacement = ((HierarchicalElement) aPlaceholderReplacer);

                if ((preference.equals(PlaceholderPreferenceEnum.Global) && theReplacement.getSignature().equals(placeholder))
                        || (preference.equals(PlaceholderPreferenceEnum.Local) && !placeholder.contains(".") && theReplacement.getName().equals(placeholder))) {

                    if(!aPlaceholderReplacer.getClass().equals(anElementWithPlaceholder.getRootType())) {
                        // replace anElementWithPlaceholder with a suitable aPlaceHolderReplacer
                        // sometimes we might need that, because at the time of the placeholder creation,
                        // we don't know if it is class or enum parameter or property.
                        if(anElementWithPlaceholder instanceof EnumerationProperty) {
                            EnumerationProperty castedProperty = ((EnumerationProperty) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Class)
                                castedProperty.changeToClassProperty(((Class) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedProperty.changeToCustomPrimitiveProperty(((CustomPrimitive) aPlaceholderReplacer));
                        } else if(anElementWithPlaceholder instanceof ClassProperty) {
                            ClassProperty castedProperty = ((ClassProperty) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Enumeration)
                                castedProperty.changeToEnumerationProperty(((Enumeration) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedProperty.changeToCustomPrimitiveProperty(((CustomPrimitive) aPlaceholderReplacer));
                        } else if(anElementWithPlaceholder instanceof EnumerationParameter) {
                            EnumerationParameter castedParameter = ((EnumerationParameter) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Class)
                                castedParameter.changeToClassParameter(((Class) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedParameter.changeToCustomPrimitiveParameter(((CustomPrimitive) aPlaceholderReplacer));
                        } else if(anElementWithPlaceholder instanceof ClassParameter) {
                            ClassParameter castedParameter = ((ClassParameter) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Enumeration)
                                castedParameter.changeToEnumerationParameter(((Enumeration) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedParameter.changeToCustomPrimitiveParameter(((CustomPrimitive) aPlaceholderReplacer));
                        }
                    } else {
                        anElementWithPlaceholder.fixType(aPlaceholderReplacer.getRealTypeOfPlaceholder());
                        replacedElement = theReplacement;
                    }
                }
            }

            // If it still has placeholders after trying to fix, we need to find by brute force.
            //    This basically might mean maybe because the package name and type name is the same,
            //       we have created a class in the same name as package.
            //          Place holder might end with class name but signatures might be different.
            if(preference.equals(PlaceholderPreferenceEnum.Global)
                    && anElementWithPlaceholder.hasPlaceholder()) {
                for(IPlaceholderReplacement aPlaceholderReplacer:allPlaceholderReplacers) {
                    HierarchicalElement theReplacement = ((HierarchicalElement) aPlaceholderReplacer);

                    if(!aPlaceholderReplacer.getClass().equals(anElementWithPlaceholder.getRootType())) {
                        // replace anElementWithPlaceholder with a suitable aPlaceHolderReplacer
                        // sometimes we might need that, because at the time of the placeholder creation,
                        // we don't know if it is class or enum parameter or property.
                        if(anElementWithPlaceholder instanceof EnumerationProperty) {
                            EnumerationProperty castedProperty = ((EnumerationProperty) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Class)
                                castedProperty.changeToClassProperty(((Class) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedProperty.changeToCustomPrimitiveProperty(((CustomPrimitive) aPlaceholderReplacer));
                        } else if(anElementWithPlaceholder instanceof ClassProperty) {
                            ClassProperty castedProperty = ((ClassProperty) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Enumeration)
                                castedProperty.changeToEnumerationProperty(((Enumeration) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedProperty.changeToCustomPrimitiveProperty(((CustomPrimitive) aPlaceholderReplacer));
                        } else if(anElementWithPlaceholder instanceof EnumerationParameter) {
                            EnumerationParameter castedParameter = ((EnumerationParameter) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Class)
                                castedParameter.changeToClassParameter(((Class) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedParameter.changeToCustomPrimitiveParameter(((CustomPrimitive) aPlaceholderReplacer));
                        } else if(anElementWithPlaceholder instanceof ClassParameter) {
                            ClassParameter castedParameter = ((ClassParameter) anElementWithPlaceholder);
                            if(aPlaceholderReplacer instanceof Enumeration)
                                castedParameter.changeToEnumerationParameter(((Enumeration) aPlaceholderReplacer));
                            else if(aPlaceholderReplacer instanceof CustomPrimitive)
                                castedParameter.changeToCustomPrimitiveParameter(((CustomPrimitive) aPlaceholderReplacer));
                        }
                    } else {
                        anElementWithPlaceholder.fixType(aPlaceholderReplacer.getRealTypeOfPlaceholder());
                        replacedElement = theReplacement;
                    }
                }
            }

            if(preference.equals(PlaceholderPreferenceEnum.Global)
                    && replacedElement!=null
                    && anElementWithPlaceholder instanceof ClassProperty) {
                ClassProperty castedProperty = ((ClassProperty) anElementWithPlaceholder);
                Association association = new Association("associationOf" + castedProperty.getName(), ((Class) castedProperty.getParent()), ((Class) replacedElement));
                addAssociation(association);

                AssociationProperty associationPropertyInSource = new AssociationProperty("assocationPropertyOf" + castedProperty.getName(), VisibilityEnum.Public, association);
                ((Class) castedProperty.getParent()).addProperty(associationPropertyInSource);
                association.addProperty(associationPropertyInSource);

                AssociationProperty associationPropertyInTarget = new AssociationProperty("associationPropertyOf" + castedProperty.getName(), VisibilityEnum.Public, association);
                ((Class) replacedElement).addProperty(associationPropertyInTarget);
                association.addProperty(associationPropertyInTarget);
            }
        }
    }

    public List<IPlaceholderedElement> collectAllPlaceholderedElements() {
        List<IPlaceholderedElement> result = new ArrayList<>();

        for(Enumeration anEnum:enumerations) {
            result.addAll(anEnum.getElementsWithPlaceholder());
        }

        for (Class aClass:classes) {
            result.addAll(aClass.getElementsWithPlaceholder());
        }

        for(Package aPackage:packages) {
            result.addAll(Package.getAllElementsWithPlaceholdersRecursively(aPackage));
        }

        return result;
    }

    public List<IPlaceholderReplacement> collectAllPlaceholderReplacements() {
        List<IPlaceholderReplacement> result = new ArrayList<>();

        result.addAll(this.classes);
        result.addAll(this.enumerations);
        result.addAll(this.customPrimitives);

        for(Package aPackage:packages) {
            result.addAll(Package.getAllPlaceholderReplacementsRecursively(aPackage));
        }

        return result;
    }

    public void applyConditions() {
        List<Class> allClasses = collectAllClasses();
        List<Operation> allOperations = new ArrayList<>();
        for (Class clasz : allClasses) {
            for (Operation operation : clasz.getOperations()) {
                allOperations.add(operation);
            }
        }

        for (Operation operation : allOperations) {
            operation.cutIfConditionNotMet();
        }
    }

    public List<Class> collectAllClasses() {
        List<Class> result = new ArrayList<Class>();

        result.addAll(this.classes);

        for(Package aPackage:packages) {
            result.addAll(aPackage.getClasses());
            for(Package aSubPackage:aPackage.getSubPackages()) {
                result.addAll(aSubPackage.getClasses());
            }
        }

        return result;
    }

    public boolean hasEnumeration(String inputEnumerationName) {
        for (Enumeration theEnum:enumerations) {
            if(theEnum.getName().equals(inputEnumerationName)) {
                return true;
            }
        }
        return false;
    }

    public Enumeration getEnumByName(String enumName) {
        for (Enumeration theEnum:enumerations) {
            if(theEnum.getName().equals(enumName)) {
                return theEnum;
            }
        }
        return null;
    }

    public Enumeration createOrGetEnumByName(String enumName) {
        Enumeration resultingEnum = null;
        if(this.hasClass(enumName)) {
            resultingEnum = this.getEnumByName(enumName);
        } else {
            resultingEnum = new Enumeration(enumName);
            this.addEnumeration(resultingEnum);
        }
        return resultingEnum;
    }

    public boolean hasClass(String inputClassName) {
        for (Class theClass:classes) {
            if(theClass.getName().equals(inputClassName)) {
                return true;
            }
        }
        return false;
    }

    public Class getClassByName(String className) {
        for (Class theClass:classes) {
            if(theClass.getName().equals(className)) {
                return theClass;
            }
        }
        return null;
    }

    public Class createOrGetClassByName(String className) {
        Class resultingClass = null;
        if(this.hasClass(className)) {
            resultingClass = this.getClassByName(className);
        } else {
            resultingClass = new Class(className);
            this.addClass(resultingClass);
        }
        return resultingClass;
    }

    public CustomPrimitive createOrGetCustomPrimitiveByName(String customPrimitiveName) {
        CustomPrimitive resultingCustomPrimitive = null;
        if(this.hasCustomPrimitive(customPrimitiveName)) {
            resultingCustomPrimitive = this.getCustomPrimitiveByName(customPrimitiveName);
        } else {
            resultingCustomPrimitive = new CustomPrimitive(customPrimitiveName);
            this.addCustomPrimitive(resultingCustomPrimitive);
        }
        return resultingCustomPrimitive;
    }

    private CustomPrimitive getCustomPrimitiveByName(String customPrimitiveName) {
        for (CustomPrimitive thePrimitive:customPrimitives) {
            if(thePrimitive.getName().equals(customPrimitiveName)) {
                return thePrimitive;
            }
        }
        return null;
    }

    private boolean hasCustomPrimitive(String customPrimitiveName) {
        for (CustomPrimitive thePrimitive:customPrimitives) {
            if(thePrimitive.getName().equals(customPrimitiveName)) {
                return true;
            }
        }
        return false;
    }

    public void addCustomPrimitive(CustomPrimitive aCustomPrimitive) {
        aCustomPrimitive.setParent(this);
        customPrimitives.add(aCustomPrimitive);
    }

    public void addEnumeration(Enumeration inputEnumeration) {
        inputEnumeration.setParent(this);
        enumerations.add(inputEnumeration);
    }

    public void addClass(Class inputClass) {
        inputClass.setParent(this);
        classes.add(inputClass);
    }

    public void addAssociation(Association association) {
        association.setParent(this);
        associations.add(association);
    }

    public boolean hasPackage(String inputPackageName) {
        for (Package thePackage:packages) {
            if(thePackage.getName().equals(inputPackageName)) {
                return true;
            }
        }
        return false;
    }

    public Package createOrGetPackageByName(String packageName) {
        Package resultingPackage = null;
        if(this.hasPackage(packageName)) {
            resultingPackage = this.getPackageByName(packageName);
        } else {
            resultingPackage = new Package(packageName);
            this.addPackage(resultingPackage);
        }
        return resultingPackage;
    }

    public Package getPackageByName(String packageName) {
        for (Package thePackage:packages) {
            if(thePackage.getName().equals(packageName)) {
                return thePackage;
            }
        }
        return null;
    }

    public void addPackage(Package inputPackage) {
        inputPackage.setParent(this);
        packages.add(inputPackage);
    }

    public String getFileName() {
        return fileName;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public List<Enumeration> getEnumerations() {
        return enumerations;
    }

    public List<CustomPrimitive> getCustomPrimitives() {
        return customPrimitives;
    }
}

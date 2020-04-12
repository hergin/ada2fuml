package model;

import exporter.Processor;
import model.enums.PlaceholderPreferenceEnum;
import model.enums.VisibilityEnum;
import model.parameters.ClassParameter;
import model.properties.AssociationProperty;
import model.properties.ClassProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UML extends HierarchicalElement {

    private String id;
    private String fileName;
    private List<Class> classes;
    private List<Package> packages;
    private List<Interface> interfaces;
    private List<Enumeration> enumerations;
    private List<Association> associations;

    public UML(String name) {
        super(name);
        id = Processor.uuidGenerator();
        classes = new ArrayList<>();
        packages = new ArrayList<>();
        interfaces = new ArrayList<>();
        enumerations = new ArrayList<>();
        associations = new ArrayList<>();
    }

    public UML(String name, String fileName) {
        this(name);
        this.fileName = fileName;
    }

    public List<HierarchicalElement> getItemsWithPlaceholders() {
        List<Class> allClasses = collectAllClasses();
        List<Class> classesThatHavePlaceholders = allClasses.stream().filter(c->c.hasPlaceholders()).collect(Collectors.toList());

        List<HierarchicalElement> result = new ArrayList<HierarchicalElement>();
        classesThatHavePlaceholders.stream().forEach(c->result.addAll(c.findElementsWithPlaceholder()));

        return result;
    }

    public void combine(UML otherUML) {
        otherUML.getClasses().forEach(c->addClass(c));

        for(Package otherPackage:otherUML.getPackages()) {
            List<Package> sameNamedPackages = packages.stream().filter(p->p.getName().equals(otherPackage.getName())).collect(Collectors.toList());
            if(sameNamedPackages.size()>0) {
                Package sameNamedPackage = sameNamedPackages.get(0);
                otherPackage.getClasses().forEach(c->sameNamedPackage.addClass(c));
                otherPackage.getSubPackages().forEach(sb->sameNamedPackage.addSubPackage(sb));
            } else {
                addPackage(otherPackage);
            }
        }

        otherUML.getAssociations().forEach(a->addAssociation(a));
    }

    public boolean hasPlaceholders() {

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

    public void fixPlaceholders(PlaceholderPreferenceEnum preference) {
        List<Class> allClasses = collectAllClasses();
        List<Class> classesThatHavePlaceholders = allClasses.stream().filter(c->c.hasPlaceholders()).collect(Collectors.toList());

        for (Class aClass:classesThatHavePlaceholders) {
            List<HierarchicalElement> elementsWithPlaceholder = aClass.findElementsWithPlaceholder();

            for (HierarchicalElement anElement:elementsWithPlaceholder) {
                String placeholder = anElement instanceof ClassProperty? ((ClassProperty) anElement).getPlaceholder(): ((ClassParameter) anElement).getPlaceholder();

                for (Class someClass:allClasses) {
                    if ((preference.equals(PlaceholderPreferenceEnum.Global) && someClass.getSignature().equals(placeholder))
                        || (preference.equals(PlaceholderPreferenceEnum.Local) && !placeholder.contains(".") && someClass.getName().equals(placeholder))) {
                        if(anElement instanceof ClassProperty) {
                            ClassProperty castedProperty = ((ClassProperty) anElement);
                            castedProperty.fixType(someClass);

                            if(preference.equals(PlaceholderPreferenceEnum.Global)) {
                                Association association = new Association("associationOf" + castedProperty.getName(), ((Class) castedProperty.getParent()), someClass);
                                addAssociation(association);

                                AssociationProperty associationPropertyInSource = new AssociationProperty("assocationPropertyOf" + castedProperty.getName(), VisibilityEnum.Public, association);
                                ((Class) castedProperty.getParent()).addProperty(associationPropertyInSource);
                                association.addProperty(associationPropertyInSource);

                                AssociationProperty associationPropertyInTarget = new AssociationProperty("associationPropertyOf" + castedProperty.getName(), VisibilityEnum.Public, association);
                                someClass.addProperty(associationPropertyInTarget);
                                association.addProperty(associationPropertyInTarget);
                            }
                        } else {
                            ClassParameter castedParameter = ((ClassParameter)anElement);
                            castedParameter.fixType(someClass);
                        }
                    }
                }
            }
        }

        // If it still has placeholders after global fixing, we need to find by brute force.
        //    This basically might mean maybe because the package name and type name is the same,
        //       we have created a class in the same name as package.
        //          Place holder might end with class name but signatures might be different.
        if(preference.equals(PlaceholderPreferenceEnum.Global) && hasPlaceholders()) {
            allClasses = collectAllClasses();
            classesThatHavePlaceholders = allClasses.stream().filter(c->c.hasPlaceholders()).collect(Collectors.toList());

            for (Class aClass:classesThatHavePlaceholders) {
                List<HierarchicalElement> elementsWithPlaceholder = aClass.findElementsWithPlaceholder();

                for (HierarchicalElement anElement:elementsWithPlaceholder) {
                    String placeholder = anElement instanceof ClassProperty? ((ClassProperty) anElement).getPlaceholder(): ((ClassParameter) anElement).getPlaceholder();

                    for (Class someClass:allClasses) {
                        if (placeholder.endsWith(someClass.getName())) {
                            if(anElement instanceof ClassProperty) {
                                ClassProperty castedProperty = ((ClassProperty) anElement);
                                castedProperty.fixType(someClass);
                                Association association = new Association("associationOf"+castedProperty.getName(), ((Class) castedProperty.getParent()),someClass);
                                addAssociation(association);

                                AssociationProperty associationPropertyInSource = new AssociationProperty("assocationPropertyOf"+castedProperty.getName(), VisibilityEnum.Public,association);
                                ((Class) castedProperty.getParent()).addProperty(associationPropertyInSource);
                                association.addProperty(associationPropertyInSource);

                                AssociationProperty associationPropertyInTarget = new AssociationProperty("associationPropertyOf"+castedProperty.getName(),VisibilityEnum.Public,association);
                                someClass.addProperty(associationPropertyInTarget);
                                association.addProperty(associationPropertyInTarget);
                            } else {
                                ClassParameter castedParameter = ((ClassParameter)anElement);
                                castedParameter.fixType(someClass);
                            }
                        }
                    }
                }
            }
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

    public String getId() {
        return id;
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
}

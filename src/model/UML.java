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
    private List<Association> associations;

    public UML(String name) {
        super(name);
        id = Processor.uuidGenerator();
        classes = new ArrayList<>();
        packages = new ArrayList<>();
        associations = new ArrayList<>();
    }

    public UML(String name, String fileName) {
        this(name);
        this.fileName = fileName;
    }

    public void combine(UML otherUML) {
        classes.addAll(otherUML.getClasses());
        packages.addAll(otherUML.getPackages());
        associations.addAll(otherUML.getAssociations());
    }

    public boolean hasPlaceholders() {

        for(var aClass:classes) {
            if(aClass.hasPlaceholders())
                return true;
        }

        for(var aPackage:packages) {
            if(aPackage.hasPlaceholders())
                return true;
        }

        return false;
    }

    public void fixPlaceholders(PlaceholderPreferenceEnum preference) {
        var allClasses = collectAllClasses();
        var classesThatHavePlaceholders = allClasses.stream().filter(c->c.hasPlaceholders()).collect(Collectors.toList());

        for (var aClass:classesThatHavePlaceholders) {
            var elementsWithPlaceholder = aClass.findElementsWithPlaceholder();

            for (var anElement:elementsWithPlaceholder) {
                String placeholder = anElement instanceof ClassProperty? ((ClassProperty) anElement).getPlaceholder(): ((ClassParameter) anElement).getPlaceholder();

                for (var someClass:allClasses) {
                    if ((preference.equals(PlaceholderPreferenceEnum.Global) && someClass.getSignature().equals(placeholder))
                        || (preference.equals(PlaceholderPreferenceEnum.Local) && !placeholder.contains(".") && someClass.getName().equals(placeholder))) {
                        if(anElement instanceof ClassProperty) {
                            var castedProperty = ((ClassProperty) anElement);
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
                            var castedParameter = ((ClassParameter)anElement);
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

            for (var aClass:classesThatHavePlaceholders) {
                var elementsWithPlaceholder = aClass.findElementsWithPlaceholder();

                for (var anElement:elementsWithPlaceholder) {
                    String placeholder = anElement instanceof ClassProperty? ((ClassProperty) anElement).getPlaceholder(): ((ClassParameter) anElement).getPlaceholder();

                    for (var someClass:allClasses) {
                        if (placeholder.endsWith(someClass.getName())) {
                            if(anElement instanceof ClassProperty) {
                                var castedProperty = ((ClassProperty) anElement);
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
                                var castedParameter = ((ClassParameter)anElement);
                                castedParameter.fixType(someClass);
                            }
                        }
                    }
                }
            }
        }
    }

    public List<Class> collectAllClasses() {
        var result = new ArrayList<Class>();

        result.addAll(this.classes);

        for(var aPackage:packages) {
            result.addAll(aPackage.getClasses());
            for(var aSubPackage:aPackage.getSubPackages()) {
                result.addAll(aSubPackage.getClasses());
            }
        }

        return result;
    }

    public boolean hasClass(String inputClassName) {
        for (var theClass:classes) {
            if(theClass.getName().equals(inputClassName)) {
                return true;
            }
        }
        return false;
    }

    public Class getClassByName(String className) {
        for (var theClass:classes) {
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
        for (var thePackage:packages) {
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
        for (var thePackage:packages) {
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

    public List<Package> getPackages() {
        return packages;
    }

    public List<Association> getAssociations() {
        return associations;
    }
}

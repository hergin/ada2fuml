package model;

import exporter.Processor;
import model.properties.ClassProperty;

import java.util.ArrayList;
import java.util.List;

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

    public void replaceLocalPlaceholders() {
        traverseClasses(classes);
        for(var packagee:packages) {
            traverseClasses(packagee.getClasses());
        }
    }

    private void traverseClasses(List<Class> incomingClasses) {
        for (var theClass:incomingClasses) {
            for (var theProperty:theClass.getProperties()) {
                if(theProperty instanceof ClassProperty) {
                    var castedProperty = ((ClassProperty) theProperty);
                    if(castedProperty.getPlaceholder() != null
                            && !castedProperty.getPlaceholder().isEmpty()
                            && !castedProperty.getPlaceholder().contains(".")) {
                        for(var expectedClass:classes) {
                            if(expectedClass.getName().equals(castedProperty.getPlaceholder())) {
                                castedProperty.fixType(expectedClass);
                                break;
                            }
                        }
                        for(var packagee:packages) {
                            for (var expectedClass : packagee.getClasses()) {
                                if (expectedClass.getName().equals(castedProperty.getPlaceholder())) {
                                    castedProperty.fixType(expectedClass);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
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

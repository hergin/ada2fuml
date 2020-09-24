package model;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.auxiliary.IPlaceholderedElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Package extends HierarchicalElement {

    private List<Property> properties;
    private List<Class> classes;
    private List<Struct> structs;
    private List<Except> exceptions;
    private List<Enumeration> enumerations;
    private List<Interface> interfaces;
    private List<Package> subPackages;

    public Package(String name) {
        super(name);
        classes = new ArrayList<>();
        structs = new ArrayList<>();
        enumerations = new ArrayList<>();
        exceptions = new ArrayList<>();
        interfaces = new ArrayList<>();
        subPackages = new ArrayList<>();
        properties = new ArrayList<>();
    }

    public static List<IPlaceholderReplacement> getAllPlaceholderReplacementsRecursively(Package aPackage) {
        List<IPlaceholderReplacement> result = new ArrayList<>();

        result.addAll(aPackage.getClasses());
        result.addAll(aPackage.getEnumerations());

        for (Package subPackage : aPackage.getSubPackages()) {
            result.addAll(Package.getAllPlaceholderReplacementsRecursively(subPackage));
        }

        return result;
    }

    public static List<IPlaceholderedElement> getAllElementsWithPlaceholdersRecursively(Package aPackage) {
        List<IPlaceholderedElement> result = new ArrayList<>();

        for(Enumeration anEnum:aPackage.getEnumerations()) {
            result.addAll(anEnum.getElementsWithPlaceholder());
        }

        for (Class aClass:aPackage.getClasses()) {
            result.addAll(aClass.getElementsWithPlaceholder());
        }

        for (Package subPackage:aPackage.getSubPackages()) {
            result.addAll(Package.getAllElementsWithPlaceholdersRecursively(subPackage));
        }

        return result;
    }

    public boolean hasPlaceholders() {

        for(Enumeration anEnum:enumerations) {
            if(anEnum.hasPlaceholders())
                return true;
        }

        for(Class classs:classes) {
            if(classs.hasPlaceholders())
                return true;
        }

        for(Package packagee:subPackages) {
            if(packagee.hasPlaceholders())
                return true;
        }

        return false;
    }

    public boolean hasClass(String inputClassName) {
        return classes.stream().filter(c->c.getName().equals(inputClassName)).count()!=0;
    }

    public boolean hasStruct(String inputStructName) {
        return structs.stream().filter(c->c.getName().equals(inputStructName)).count()!=0;
    }

    public boolean hasInterface(String inputInterfacesName) {
        return interfaces.stream().filter(c->c.getName().equals(inputInterfacesName)).count()!=0;
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

    public Struct createOrGetStructByName(String structName) {
        Struct resultingStruct = null;
        if(this.hasStruct(structName)) {
            resultingStruct = this.getStructByName(structName);
        } else {
            resultingStruct = new Struct(structName);
            this.addStruct(resultingStruct);
        }
        return resultingStruct;
    }

    public Interface createOrGetInterfaceByName(String interfaceName) {
        Interface resultingInterface = null;
        if(this.hasInterface(interfaceName)) {
        	resultingInterface = this.getInterfaceByName(interfaceName);
        } else {
        	resultingInterface = new Interface(interfaceName);
            this.addInterface(resultingInterface);
        }
        return resultingInterface;
    }

    public Class getClassByName(String className) {
        return classes.stream().filter(c->c.getName().equals(className)).findFirst().get();
    }

    public Except getExceptByName(String exceptName) {
        return exceptions.stream().filter(c->c.getName().equals(exceptName)).findFirst().get();
    }

    public Struct getStructByName(String structName) {
        return structs.stream().filter(c->c.getName().equals(structName)).findFirst().get();
    }

    public Enumeration getEnumByName(String enumName) {
        return enumerations.stream().filter(c->c.getName().equals(enumName)).findFirst().get();
    }

    public Interface getInterfaceByName(String interfaceName) {
        return interfaces.stream().filter(c->c.getName().equals(interfaceName)).findFirst().get();
    }

    public void addProperty(Property someProperty) {
        someProperty.setParent(this);
        properties.add(someProperty);
    }

    public void addClass(Class inputClass) {
        inputClass.setParent(this);
        classes.add(inputClass);
    }

    public void addStruct(Struct inputStruct) {
        inputStruct.setParent(this);
        structs.add(inputStruct);
    }

    public void addExcept(Except inputExcept) {
        inputExcept.setParent(this);
        exceptions.add(inputExcept);
    }

    public void addEnumeration(Enumeration inputEnumeration) {
    	inputEnumeration.setParent(this);
        enumerations.add(inputEnumeration);
    }

    public void addInterface(Interface inputInterface) {
    	inputInterface.setParent(this);
        interfaces.add(inputInterface);
    }

    public boolean hasSubPackage(String inputPackageName) {
        return subPackages.stream().filter(p->p.getName().equals(inputPackageName)).count()!=0;
    }

    public Package createOrGetSubPackageByName(String packageName) {
        Package resultingPackage = null;
        if(this.hasSubPackage(packageName)) {
            resultingPackage = this.getSubPackageByName(packageName);
        } else {
            resultingPackage = new Package(packageName);
            this.addSubPackage(resultingPackage);
        }
        return resultingPackage;
    }

    public Package getSubPackageByName(String packageName) {
        return subPackages.stream().filter(p->p.getName().equals(packageName)).findFirst().get();
    }

    public void addSubPackage(Package inputPackage) {
        inputPackage.setParent(this);
        subPackages.add(inputPackage);
    }

    public boolean hasProperties() {
        return (properties != null) && (! properties.isEmpty());
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public List<Struct> getStructs() {
        return structs;
    }

    public List<Except> getExceptions() {
        return exceptions;
    }

    public List<Enumeration> getEnumerations() {
        return enumerations;
    }

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    public List<Package> getSubPackages() {
        return subPackages;
    }

}

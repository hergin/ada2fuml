package model;

import exporter.Processor;

import java.util.ArrayList;
import java.util.List;

public class UML {

    private String id;
    private List<Class> classes;
    private List<Package> packages;
    private List<Association> associations;
    private String name;

    public UML(String name) {
        id = Processor.uuidGenerator();
        this.name = name;
        classes = new ArrayList<>();
        packages = new ArrayList<>();
        associations = new ArrayList<>();
    }

    public boolean hasClass(String inputClassName) {
        return classes.stream().filter(c->c.getName()==inputClassName).count()!=0;
    }

    public Class getClassByName(String className) {
        return classes.stream().filter(c->c.getName()==className).findFirst().get();
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
        classes.add(inputClass);
    }

    public void addAssociation(Association association) {
        associations.add(association);
    }

    public boolean hasPackage(String inputPackageName) {
        return packages.stream().filter(p->p.getName()==inputPackageName).count()!=0;
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
        return packages.stream().filter(p->p.getName()==packageName).findFirst().get();
    }

    public void addPackage(Package inputPackage) {
        packages.add(inputPackage);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return this.name;
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

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

    public void addClass(Class inputClass) {
        classes.add(inputClass);
    }

    public void addAssociation(Association association) {
        associations.add(association);
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

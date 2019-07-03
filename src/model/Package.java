package model;

import exporter.Processor;

import java.util.ArrayList;
import java.util.List;

public class Package {

    private List<Class> classes;
    private List<Package> subPackages;
    private String name;
    private String id;

    public Package(String name) {
        id = Processor.uuidGenerator();
        this.name = name;
        classes = new ArrayList<>();
        subPackages = new ArrayList<>();
    }

    public void addClass(Class inputClass) {
        classes.add(inputClass);
    }

    public void addSubPackage(Package inputPackage) { subPackages.add(inputPackage); }

    public List<Class> getClasses() {
        return classes;
    }

    public List<Package> getSubPackages() {
        return subPackages;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

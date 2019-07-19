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

    public boolean hasClass(String inputClassName) {
        return classes.stream().filter(c->c.getName()==inputClassName).count()!=0;
    }

    public Class getClassByName(String className) {
        return classes.stream().filter(c->c.getName()==className).findFirst().get();
    }

    public void addClass(Class inputClass) {
        classes.add(inputClass);
    }

    public boolean hasSubPackage(String inputPackageName) {
        return subPackages.stream().filter(p->p.getName()==inputPackageName).count()!=0;
    }

    public Package getSubPackageByName(String packageName) {
        return subPackages.stream().filter(p->p.getName()==packageName).findFirst().get();
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

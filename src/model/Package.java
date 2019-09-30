package model;

import exporter.Processor;

import java.util.ArrayList;
import java.util.List;

public class Package extends HierarchicalElement {

    private List<Class> classes;
    private List<Package> subPackages;
    private String id;

    public Package(String name) {
        super(name);
        id = Processor.uuidGenerator();
        classes = new ArrayList<>();
        subPackages = new ArrayList<>();
    }

    public boolean hasPlaceholders() {
        for(var classs:classes) {
            if(classs.hasPlaceholders())
                return true;
        }

        for(var packagee:subPackages) {
            if(packagee.hasPlaceholders())
                return true;
        }

        return false;
    }

    public boolean hasClass(String inputClassName) {
        return classes.stream().filter(c->c.getName().equals(inputClassName)).count()!=0;
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

    public Class getClassByName(String className) {
        return classes.stream().filter(c->c.getName().equals(className)).findFirst().get();
    }

    public void addClass(Class inputClass) {
        inputClass.setParent(this);
        classes.add(inputClass);
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

    public List<Class> getClasses() {
        return classes;
    }

    public List<Package> getSubPackages() {
        return subPackages;
    }

    public String getId() {
        return id;
    }
}

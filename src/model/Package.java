package model;

import model.auxiliary.HierarchicalElement;
import model.auxiliary.IPlaceholderReplacement;
import model.auxiliary.IPlaceholderedElement;

import java.util.ArrayList;
import java.util.List;

public class Package extends HierarchicalElement {

    private List<AbstractProperty> properties;
    private List<Class> classes;
    private List<Struct> structs;
    private List<Except> exceptions;
    private List<Array> arrays;
    private List<Enumeration> enumerations;
    private List<Interface> interfaces;
    private List<Package> subPackages;
    private List<CustomPrimitive> customPrimitives;

    public Package() {
        this("");
    }

    public Package(String name) {
        super(name);
        classes = new ArrayList<>();
        structs = new ArrayList<>();
        arrays = new ArrayList<>();
        enumerations = new ArrayList<>();
        exceptions = new ArrayList<>();
        interfaces = new ArrayList<>();
        subPackages = new ArrayList<>();
        properties = new ArrayList<>();
        customPrimitives = new ArrayList<>();
    }

    public static List<IPlaceholderReplacement> getAllPlaceholderReplacementsRecursively(Package aPackage) {
        List<IPlaceholderReplacement> result = new ArrayList<>();

        result.addAll(aPackage.getClasses());
        result.addAll(aPackage.getEnumerations());
        result.addAll(aPackage.getCustomPrimitives());

        for (Package subPackage : aPackage.getSubPackages()) {
            result.addAll(Package.getAllPlaceholderReplacementsRecursively(subPackage));
        }

        return result;
    }

    public static List<HierarchicalElement> getAllElementsWithReferencesRecursively(Package aPackage) {
        List<HierarchicalElement> result = new ArrayList<>();

        for (Class aClass:aPackage.getClasses()) {
            result.addAll(aClass.getElementsWithReferences());
        }

        for (Package subPackage:aPackage.getSubPackages()) {
            result.addAll(Package.getAllElementsWithReferencesRecursively(subPackage));
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



    public Struct findStructByName(String structName, String delimiter) {
        if (structs.stream().filter(c->c.getName().equals(structName)).count()!=0) {
            return structs.stream().filter(c->c.getName().equals(structName)).findFirst().get();
        }

        if ( (delimiter == null) || (delimiter.isEmpty()) ) {
        	return null;
        }

	    String[] components = structName.split(delimiter);
	    int numComponents = components.length;  // all are package names except last, which is the type name
	    if (numComponents <= 1) {
	    	// There is only one component in structName, but the above search did not it
	    	// Return null to indicate that it didn't find structName
	    	return null;
	    }

	    // There must be multiple components in struct name
	    String baseComponent = components[0];
	    String typeName = components[numComponents - 1];
	    String ourName = this.getName();

	    if (baseComponent.equals(ourName)) {
	        if (structs.stream().filter(c->c.getName().equals(typeName)).count()!=0) {
	            return structs.stream().filter(c->c.getName().equals(typeName)).findFirst().get();
	        }

	        if (numComponents == 2) {
		        // There are only two components so typeName must be in this package
	        	// We reached here only because typeName wasn't found. Thus it is null
		        return null;
	        }

	        List<String> stringList = new ArrayList<>();
	        for (int i = 2; i<numComponents; i++) {
	        	stringList.add(components[i]);
	        }

	        String partialName = String.join(delimiter, stringList);
	    	try {
	            Package subPackage = getSubPackageByName(components[1]);
	            if (subPackage != null) {
	            	return subPackage.findStructByName(partialName, delimiter);
	            }

	    	} catch (java.util.NoSuchElementException bitBucket) {
			}	        
	    }

    	try {
            Package subPackage = getSubPackageByName(baseComponent);
            if (subPackage != null) {
            	return subPackage.findStructByName(structName, delimiter);
            }

    	} catch (java.util.NoSuchElementException bitBucket) {
		}

        // This component is not baseComponent and subPackage is not baseComponent
        // Let's hope that baseComponent can be found in parent hierarchy
	    HierarchicalElement parentElement = getParent();
    	if (parentElement == null) {
    		return null;
    	}

    	try {
        	Package parentPackage = (Package) parentElement;
        	return parentPackage.findStructByName(structName, delimiter);

    	} catch (java.util.NoSuchElementException bitBucket) {
			return null;
		}
    }




    public Array getArrayByName(String arrayName) {
        return arrays.stream().filter(c->c.getName().equals(arrayName)).findFirst().get();
    }



    public Array findArrayByName(String arrayName, String delimiter) {
        if (arrays.stream().filter(c->c.getName().equals(arrayName)).count()!=0) {
            return arrays.stream().filter(c->c.getName().equals(arrayName)).findFirst().get();
        }

        if ( (delimiter == null) || (delimiter.isEmpty()) ) {
        	return null;
        }

	    String[] components = arrayName.split(delimiter);
	    int numComponents = components.length;  // all are package names except last, which is the type name
	    if (numComponents <= 1) {
	    	// There is only one component in arrayName, but the above search did not it
	    	// Return null to indicate that it didn't find arrayName
	    	return null;
	    }

	    // There must be multiple components in array name
	    String baseComponent = components[0];
	    String typeName = components[numComponents - 1];
	    String ourName = this.getName();

	    if (baseComponent.equals(ourName)) {
	        if (arrays.stream().filter(c->c.getName().equals(typeName)).count()!=0) {
	            return arrays.stream().filter(c->c.getName().equals(typeName)).findFirst().get();
	        }

	        if (numComponents == 2) {
		        // There are only two components so typeName must be in this package
	        	// We reached here only because typeName wasn't found. Thus it is null
		        return null;
	        }

	        List<String> stringList = new ArrayList<>();
	        for (int i = 2; i<numComponents; i++) {
	        	stringList.add(components[i]);
	        }

	        String partialName = String.join(delimiter, stringList);
	    	try {
	            Package subPackage = getSubPackageByName(components[1]);
	            if (subPackage != null) {
	            	return subPackage.findArrayByName(partialName, delimiter);
	            }

	    	} catch (java.util.NoSuchElementException bitBucket) {
			}	        
	    }

    	try {
            Package subPackage = getSubPackageByName(baseComponent);
            if (subPackage != null) {
            	return subPackage.findArrayByName(arrayName, delimiter);
            }

    	} catch (java.util.NoSuchElementException bitBucket) {
		}

        // This component is not baseComponent and subPackage is not baseComponent
        // Let's hope that baseComponent can be found in parent hierarchy
	    HierarchicalElement parentElement = getParent();
    	if (parentElement == null) {
    		return null;
    	}

    	try {
        	Package parentPackage = (Package) parentElement;
        	return parentPackage.findArrayByName(arrayName, delimiter);

    	} catch (java.util.NoSuchElementException bitBucket) {
			return null;
		}
    }




    public Enumeration getEnumByName(String enumName) {
        return enumerations.stream().filter(c->c.getName().equals(enumName)).findFirst().get();
    }



    public Enumeration findEnumByName(String enumName, String delimiter) {
        if (enumerations.stream().filter(c->c.getName().equals(enumName)).count()!=0) {
            return enumerations.stream().filter(c->c.getName().equals(enumName)).findFirst().get();
        }

        if ( (delimiter == null) || (delimiter.isEmpty()) ) {
        	return null;
        }

	    String[] components = enumName.split(delimiter);
	    int numComponents = components.length;  // all are package names except last, which is the type name
	    if (numComponents <= 1) {
	    	// There is only one component in enumName, but the above search did not it
	    	// Return null to indicate that it didn't find enumName
	    	return null;
	    }

	    // There must be multiple components in enum name
	    String baseComponent = components[0];
	    String typeName = components[numComponents - 1];
	    String ourName = this.getName();

	    if (baseComponent.equals(ourName)) {
	        if (enumerations.stream().filter(c->c.getName().equals(typeName)).count()!=0) {
	            return enumerations.stream().filter(c->c.getName().equals(typeName)).findFirst().get();
	        }

	        if (numComponents == 2) {
		        // There are only two components so typeName must be in this package
	        	// We reached here only because typeName wasn't found. Thus it is null
		        return null;
	        }

	        List<String> stringList = new ArrayList<>();
	        for (int i = 2; i<numComponents; i++) {
	        	stringList.add(components[i]);
	        }

	        String partialName = String.join(delimiter, stringList);
	    	try {
	            Package subPackage = getSubPackageByName(components[1]);
	            if (subPackage != null) {
	            	return subPackage.findEnumByName(partialName, delimiter);
	            }

	    	} catch (java.util.NoSuchElementException bitBucket) {
			}	        
	    }

    	try {
            Package subPackage = getSubPackageByName(baseComponent);
            if (subPackage != null) {
            	return subPackage.findEnumByName(enumName, delimiter);
            }

    	} catch (java.util.NoSuchElementException bitBucket) {
		}

        // This component is not baseComponent and subPackage is not baseComponent
        // Let's hope that baseComponent can be found in parent hierarchy
	    HierarchicalElement parentElement = getParent();
    	if (parentElement == null) {
    		return null;
    	}

    	try {
        	Package parentPackage = (Package) parentElement;
        	return parentPackage.findEnumByName(enumName, delimiter);

    	} catch (java.util.NoSuchElementException bitBucket) {
			return null;
		}
    }




    public Interface getInterfaceByName(String interfaceName) {
        return interfaces.stream().filter(c->c.getName().equals(interfaceName)).findFirst().get();
    }

    public void addProperty(AbstractProperty someProperty) {
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

    public void addArray(Array inputArray) {
    	inputArray.setParent(this);
        arrays.add(inputArray);
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

    public void addCustomPrimitive(CustomPrimitive aCustomPrimitive) {
        aCustomPrimitive.setParent(this);
        customPrimitives.add(aCustomPrimitive);
    }

    public boolean hasProperties() {
        return (properties != null) && (! properties.isEmpty());
    }

    public List<AbstractProperty> getProperties() {
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

    public List<Array> getArrays() {
        return arrays;
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

    public List<CustomPrimitive> getCustomPrimitives() {
        return customPrimitives;
    }

}

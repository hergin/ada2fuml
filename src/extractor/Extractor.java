package extractor;

import adaschema.*;
import exceptions.InvalidSignatureException;
import exceptions.NamingException;
import exceptions.PartialUMLException;
import exceptions.UnhandledTypeException;
//import exceptions.UnknownTypeException;
import model.*;
import model.Class;
import model.EnumerationLiteral;
import model.Package;
import model.enums.DirectionEnum;
import model.enums.PlaceholderPreferenceEnum;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.parameters.ClassParameter;
import model.parameters.PrimitiveParameter;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import xmlparsing.GenericXmlParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

public class Extractor {

    public static UML extractHighLevelConcepts(Document document) throws InvalidSignatureException {
        UML resultingUML = new UML(document.getDocumentElement().getAttribute("def_name"), document.getDocumentElement().getAttribute("source_file"));

        List<Node> packageDeclarations = GenericXmlParser.getNodesWithSignature(document, "compilation_unit>unit_declaration_q>package_declaration");
        if (packageDeclarations.size() > 0) {
            Node packageDeclaration = packageDeclarations.get(0);
            String packageName = GenericXmlParser.getNodesWithSignatureStartingFrom(packageDeclaration, "package_declaration>names_ql>defining_identifier").get(0).getAttributes().getNamedItem("def_name").getNodeValue();
            String classNameAfterPackageName = packageName;

            // If this is true, then we will create the package name just before the _ but everything else will remain same.
            //      check the excel file by ROY to see what happens the naming when underscore is involved for more info.
            if (packageName.contains("_")) {
                packageName = packageName.split("_")[0];
            }

            List<Node> ordinaryTypeDeclarations = GenericXmlParser.getNodesWithSignatureStartingFrom(packageDeclaration, "package_declaration>visible_part_declarative_items_ql>ordinary_type_declaration");
            // combine with private part types
            ordinaryTypeDeclarations.addAll(GenericXmlParser.getNodesWithSignatureStartingFrom(packageDeclaration, "package_declaration>private_part_declarative_items_ql>ordinary_type_declaration"));

            for (Node ordinaryType : ordinaryTypeDeclarations) {
                String typeName = GenericXmlParser.getNodesWithSignatureStartingFrom(ordinaryType, "ordinary_type_declaration>names_ql>defining_identifier").get(0).getAttributes().getNamedItem("def_name").getNodeValue();
                Package thePackageInWhichTheTypeClass = null;
                Class theClass = null;

                // packageX + typeX => classX
                if (packageName.equals(typeName)) {
                    theClass = resultingUML.createOrGetClassByName(typeName);
                }

                // If there is a . in the package name, we will create subpackage.
                if (classNameAfterPackageName.contains(".")) {
                    String higherLevelPackage = classNameAfterPackageName.split("\\.")[0];
                    packageName = higherLevelPackage;
                    if (higherLevelPackage.contains("_"))
                        higherLevelPackage = higherLevelPackage.split("_")[0];
                    classNameAfterPackageName = classNameAfterPackageName.split("\\.")[1];

                    Package dottedSuperPackage = resultingUML.createOrGetPackageByName(higherLevelPackage);

                    // packageX + typeY => packageX + classY
                    if (!packageName.equals(typeName)) {
                        Package subpackage = dottedSuperPackage.createOrGetSubPackageByName(packageName);
                        theClass = subpackage.createOrGetClassByName(typeName);
                        thePackageInWhichTheTypeClass = subpackage;
                    }

                } else {
                    // packageX + typeY => packageX + classY
                    if (!packageName.equals(typeName)) {
                        Package packageNamedAfterAdaPackage = resultingUML.createOrGetPackageByName(packageName);
                        theClass = packageNamedAfterAdaPackage.createOrGetClassByName(typeName);
                        thePackageInWhichTheTypeClass = packageNamedAfterAdaPackage;
                    }
                }

                if (theClass != null) {
                    // TODO extract components
                }
            }
        }

        return resultingUML;
    }

    public static UML extractHighLevelConcepts(CompilationUnit compilationUnit) throws NamingException, PartialUMLException {
        UML resultingUML = new UML(compilationUnit.getDefName(), compilationUnit.getSourceFile());

        List<Exception> exceptions = new ArrayList<>();

        if (compilationUnit.getUnitDeclarationQ().getPackageDeclaration() != null) {

            PackageDeclaration thePackage = compilationUnit.getUnitDeclarationQ().getPackageDeclaration();
            String packageName = thePackage.getName();
            String classNameAfterPackageName = packageName;

            // If this is true, then we will create the package name just before the _ but everything else will remain same.
            //      check the excel file by ROY to see what happens the naming when underscore is involved for more info.
            if (packageName.contains("_")) {
                packageName = packageName.split("_")[0];
            }

            for (SubtypeDeclaration theSubtype : thePackage.getSubtypes()) {
                String subtypeName = theSubtype.getName();

                CustomPrimitive thePrimitive = null;
                Package thePackageInWhichTheSubTypeClass = null;

                // packageX + typeX => classX
                if (packageName.equals(subtypeName)) {
                    thePrimitive = resultingUML.createOrGetCustomPrimitiveByName(subtypeName);
                }

                // If there is a . in the package name, we will create subpackage.
                if (classNameAfterPackageName.contains(".")) {
                    String higherLevelPackage = classNameAfterPackageName.split("\\.")[0];
                    packageName = higherLevelPackage;
                    if (higherLevelPackage.contains("_"))
                        higherLevelPackage = higherLevelPackage.split("_")[0];
                    classNameAfterPackageName = classNameAfterPackageName.split("\\.")[1];

                    Package dottedSuperPackage = resultingUML.createOrGetPackageByName(higherLevelPackage);

                    // packageX + typeY => packageX + classY
                    if (!packageName.equals(subtypeName)) {
                        Package subpackage = dottedSuperPackage.createOrGetSubPackageByName(packageName);
                        thePackageInWhichTheSubTypeClass = subpackage;
                        thePrimitive = subpackage.createOrGetCustomPrimitiveByName(subtypeName);
                    }

                } else {
                    // packageX + typeY => packageX + classY
                    if (!packageName.equals(subtypeName)) {
                        Package packageNamedAfterAdaPackage = resultingUML.createOrGetPackageByName(packageName);
                        thePackageInWhichTheSubTypeClass = packageNamedAfterAdaPackage;
                        thePrimitive = packageNamedAfterAdaPackage.createOrGetCustomPrimitiveByName(subtypeName);
                    }
                }

                String theSuperPrimitiveName = theSubtype.getSuperPrimitiveName();
                if (isPrimitive(theSuperPrimitiveName)) {
                    thePrimitive.setSuperPrimitive(convertToTypeEnum(theSuperPrimitiveName));
                } else {
                    CustomPrimitive superCustomPrimitive = thePackageInWhichTheSubTypeClass.createOrGetCustomPrimitiveByName(theSuperPrimitiveName);
                    thePrimitive.addSuperCustomPrimitive(superCustomPrimitive);
                }
            }

            for (OrdinaryTypeDeclaration theEnumerationOrdinaryType : thePackage.getEnumerationOrdinaryTypes()) {
                String enumName = theEnumerationOrdinaryType.getName();

                Enumeration theEnum = null;
                Package thePackageInWhichEnumIs = null;

                // packageX + typeX => classX
                if (packageName.equals(enumName)) {
                    theEnum = resultingUML.createOrGetEnumByName(enumName);
                }

                // If there is a . in the package name, we will create subpackage.
                if (classNameAfterPackageName.contains(".")) {
                    String higherLevelPackage = classNameAfterPackageName.split("\\.")[0];
                    packageName = higherLevelPackage;
                    if (higherLevelPackage.contains("_"))
                        higherLevelPackage = higherLevelPackage.split("_")[0];
                    classNameAfterPackageName = classNameAfterPackageName.split("\\.")[1];

                    Package dottedSuperPackage = resultingUML.createOrGetPackageByName(higherLevelPackage);

                    // packageX + typeY => packageX + classY
                    if (!packageName.equals(enumName)) {
                        Package subpackage = dottedSuperPackage.createOrGetSubPackageByName(packageName);
                        thePackageInWhichEnumIs = subpackage;
                        theEnum = subpackage.createOrGetEnumByName(enumName);
                    }

                } else {
                    // packageX + typeY => packageX + classY
                    if (!packageName.equals(enumName)) {
                        Package packageNamedAfterAdaPackage = resultingUML.createOrGetPackageByName(packageName);
                        thePackageInWhichEnumIs = packageNamedAfterAdaPackage;
                        theEnum = packageNamedAfterAdaPackage.createOrGetEnumByName(enumName);
                    }
                }

                for (String anEnumLiteral : theEnumerationOrdinaryType.getEnumLiterals()) {
                    theEnum.addLiteral(new EnumerationLiteral(anEnumLiteral));
                }

            }

            for (OrdinaryTypeDeclaration theType : thePackage.getOrdinaryTypesOtherThanEnumeration()) {

                try {

                    String typeName = theType.getName();
                    Package thePackageInWhichTheSubTypeClass = null;
                    Class theClass = null;

                    // packageX + typeX => classX
                    if (packageName.equals(typeName)) {
                        theClass = resultingUML.createOrGetClassByName(typeName);
                    }

                    // If there is a . in the package name, we will create subpackage.
                    if (classNameAfterPackageName.contains(".")) {
                        String higherLevelPackage = classNameAfterPackageName.split("\\.")[0];
                        packageName = higherLevelPackage;
                        if (higherLevelPackage.contains("_"))
                            higherLevelPackage = higherLevelPackage.split("_")[0];
                        classNameAfterPackageName = classNameAfterPackageName.split("\\.")[1];

                        Package dottedSuperPackage = resultingUML.createOrGetPackageByName(higherLevelPackage);

                        // packageX + typeY => packageX + classY
                        if (!packageName.equals(typeName)) {
                            Package subpackage = dottedSuperPackage.createOrGetSubPackageByName(packageName);
                            theClass = subpackage.createOrGetClassByName(typeName);
                            thePackageInWhichTheSubTypeClass = subpackage;
                        }

                    } else {
                        // packageX + typeY => packageX + classY
                        if (!packageName.equals(typeName)) {
                            Package packageNamedAfterAdaPackage = resultingUML.createOrGetPackageByName(packageName);
                            theClass = packageNamedAfterAdaPackage.createOrGetClassByName(typeName);
                            thePackageInWhichTheSubTypeClass = packageNamedAfterAdaPackage;
                        }
                    }

                    if (theClass != null) {
                        List<ComponentDeclaration> components = theType.getComponentDeclarations();

                        if (components.isEmpty()) {
                            // TODO this means it is an unhandled type in TypeDeclarationViewQ.
                            //      Class will be in the resulting UML but will probably be empty.
                            //      These are still OrdinaryTypeDeclarations, so to fill the details one might need to go into more details.
                            java.lang.Class classDef = theType.getTypeDeclarationViewQ().getClass();
                            Field[] fieldDefs = classDef.getDeclaredFields();

                            for (Field fieldDef : fieldDefs) {
                                Object value = null;
                                fieldDef.setAccessible(true);
                                try {
                                    value = fieldDef.get(theType.getTypeDeclarationViewQ());
                                } catch (IllegalAccessException e) {
                                    //e.printStackTrace();
                                }
                                if (value != null) {
                                    if (!fieldDef.getName().equals("recordTypeDefinition")
                                            && !fieldDef.getName().equals("taggedRecordTypeDefinition")
                                            && !fieldDef.getName().equals("derivedRecordExtensionDefinition")
                                            && !fieldDef.getName().equals("unconstrainedArrayDefinition")
                                            && !fieldDef.getName().equals("derivedTypeDefinition")) {
                                        throw new UnhandledTypeException("An unhandled type is found in TypeDeclarationViewQ: " + fieldDef.getName());
                                    }
                                }
                            }

                        }

                        for (ComponentDeclaration component : components) {
                            String name = component.getName();
                            String type = component.getType();

                            //var visibility = null; // TODO FIND IT

                            if (isPrimitive(type)) {
                                TypeEnum typeEnum = convertToTypeEnum(type);

                                Object defaultValue = null;

                                if (component.getInitializationExpressionQ() != null) {
                                    switch (typeEnum) {
                                        case Integer:
                                            if (component.getInitializationExpressionQ().getIntegerLiteral() != null)
                                                defaultValue = Integer.parseInt(component.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                            break;
                                        case Boolean:
                                            if (component.getInitializationExpressionQ().getEnumerationLiteral() != null)
                                                defaultValue = Boolean.parseBoolean(component.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                            break;
                                        case Real:
                                            if (component.getInitializationExpressionQ().getRealLiteral() != null)
                                                defaultValue = Double.parseDouble(component.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                            break;
                                        case UnlimitedNatural:
                                            if (component.getInitializationExpressionQ().getIntegerLiteral() != null)
                                                defaultValue = Integer.parseInt(component.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                            break;
                                        case String:
                                            if (component.getInitializationExpressionQ().getStringLiteral() != null)
                                                defaultValue = component.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                            break;
                                        default:
                                            defaultValue = null;
                                    }
                                }

                                PrimitiveProperty primitiveProperty = new PrimitiveProperty(name, VisibilityEnum.Public, typeEnum, defaultValue);
                                theClass.addProperty(primitiveProperty);
                            } else {
                                ClassProperty classProperty = new ClassProperty(name, VisibilityEnum.Public, type);
                                theClass.addProperty(classProperty);
                            }
                        }

                        String superClassName = theType.getSuperClassName();
                        if (superClassName != null) {
                            theClass.addSuperClass(thePackageInWhichTheSubTypeClass.createOrGetClassByName(superClassName));
                        }
                    }
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }

            for (VariableDeclaration theVariable : thePackage.getVariableDeclarations()) {

                try {

                    String variableName = theVariable.getName();
                    String variableType = theVariable.getType();

                    //var variableVisibility = null; // TODO FIND IT

                    if (isPrimitive(variableType)) {
                        TypeEnum typeEnum = convertToTypeEnum(variableType);

                        Object variableDefaultValue = null;

                        if (theVariable.getInitializationExpressionQ() != null) {
                            switch (typeEnum) {
                                case Integer:
                                    if (theVariable.getInitializationExpressionQ().getIntegerLiteral() != null)
                                        variableDefaultValue = Integer.parseInt(theVariable.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                    break;
                                case Boolean:
                                    if (theVariable.getInitializationExpressionQ().getEnumerationLiteral() != null)
                                        variableDefaultValue = Boolean.parseBoolean(theVariable.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                    break;
                                case Real:
                                    if (theVariable.getInitializationExpressionQ().getRealLiteral() != null)
                                        variableDefaultValue = Double.parseDouble(theVariable.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                    break;
                                case UnlimitedNatural:
                                    if (theVariable.getInitializationExpressionQ().getIntegerLiteral() != null)
                                        variableDefaultValue = Integer.parseInt(theVariable.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                    break;
                                case String:
                                    if (theVariable.getInitializationExpressionQ().getStringLiteral() != null)
                                        variableDefaultValue = theVariable.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                    break;
                                default:
                                    variableDefaultValue = null;
                            }
                        }

                        PrimitiveProperty primitiveProperty = new PrimitiveProperty(variableName, VisibilityEnum.Public, typeEnum, variableDefaultValue);
                        // Put the variable without any type to a class same named with the package
                        Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                        classNamedAfterAdaPackage.addProperty(primitiveProperty);
                    } else {
                        ClassProperty classProperty = new ClassProperty(variableName, VisibilityEnum.Public, variableType);
                        // Put the variable without any type to a class same named with the package
                        Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                        classNamedAfterAdaPackage.addProperty(classProperty);
                    }
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }

            for (FunctionDeclaration theFunction : thePackage.getFunctionDeclarations()) {

                try {

                    String functionName = theFunction.getName();
                    String returnType = theFunction.getReturnType();

                    Operation theOperation = new Operation(functionName, VisibilityEnum.Public);

                    // Identify return type and add it as parameter
                    if (isPrimitive(returnType)) {
                        TypeEnum typeEnum = convertToTypeEnum(returnType);
                        PrimitiveParameter primitiveParameter = new PrimitiveParameter("return", DirectionEnum.Return, typeEnum);
                        theOperation.addParameter(primitiveParameter);
                    } else {
                        ClassParameter classParameter = new ClassParameter(functionName + "_Return", DirectionEnum.Return, returnType);
                        theOperation.addParameter(classParameter);
                    }

                    // Process regular parameters of the function
                    for (ParameterSpecification theParameter : theFunction.getParameterSpecifications()) {
                        String parameterName = theParameter.getName();
                        String parameterMode = theParameter.getMode();
                        DirectionEnum directionEnum = convertToDirectionEnum(parameterMode);
                        String parameterType = theParameter.getType();

                        if (isPrimitive(parameterType)) {
                            TypeEnum typeEnum = convertToTypeEnum(parameterType);

                            Object parameterDefaultValue = null;

                            if (theParameter.getInitializationExpressionQ() != null) {
                                switch (typeEnum) {
                                    case Integer:
                                        if (theParameter.getInitializationExpressionQ().getIntegerLiteral() != null)
                                            parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                        break;
                                    case Boolean:
                                        if (theParameter.getInitializationExpressionQ().getEnumerationLiteral() != null)
                                            parameterDefaultValue = Boolean.parseBoolean(theParameter.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                        break;
                                    case Real:
                                        if (theParameter.getInitializationExpressionQ().getRealLiteral() != null)
                                            parameterDefaultValue = Double.parseDouble(theParameter.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                        break;
                                    case UnlimitedNatural:
                                        if (theParameter.getInitializationExpressionQ().getIntegerLiteral() != null)
                                            parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                        break;
                                    case String:
                                        if (theParameter.getInitializationExpressionQ().getStringLiteral() != null)
                                            parameterDefaultValue = theParameter.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                        break;
                                    default:
                                        parameterDefaultValue = null;
                                }
                            }

                            PrimitiveParameter primitiveParameter = new PrimitiveParameter(parameterName, directionEnum, typeEnum, parameterDefaultValue);
                            theOperation.addParameter(primitiveParameter);
                        } else {
                            ClassParameter classParameter = new ClassParameter(parameterName, directionEnum, parameterType);
                            theOperation.addParameter(classParameter);
                        }
                    }

                    // IDENTIFY WHERE TO PUT THIS OPERATION

                    // If there is only 1 parameter, it is just return.
                    // Should be put to the class named after the package
                    if (theOperation.getParameters().size() == 1) {
                        Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                        classNamedAfterAdaPackage.addOperation(theOperation);
                    }

                    // If there are more than 1 parameter, let's check the type of the first parameter.
                    // first parameter is the one after return parameter, so index is 1
                    if (theOperation.getParameters().size() > 1) {
                        Parameter firstParameter = theOperation.getParameters().get(1);

                        // If first parameter is primitive, then it is like no parameter than return.
                        // Else, fix the class of the first parameter and put it to that class as an operation
                        //      If type couldn't be fixed, then, put it to the classNamedAfterAdaPackage again
                        if (firstParameter instanceof PrimitiveParameter) {
                            Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                            classNamedAfterAdaPackage.addOperation(theOperation);
                        } else {
                            ClassParameter castedParameter = ((ClassParameter) firstParameter);
                            for (Class aClass : resultingUML.collectAllClasses()) {
                                if (!castedParameter.getPlaceholder().contains(".") && aClass.getName().equals(castedParameter.getPlaceholder())) {
                                    castedParameter.fixType(aClass);
                                }
                            }
                            if (castedParameter.getType() != null)
                                castedParameter.getType().addOperation(theOperation);
                            else {
                                Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                                classNamedAfterAdaPackage.addOperation(theOperation);
                            }
                        }
                    }
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }


            for (ProcedureDeclaration theProcedure : thePackage.getProcedureDeclarations()) {

                try {

                    String functionName = theProcedure.getName();

                    Operation theOperation = new Operation(functionName, VisibilityEnum.Public);

                    // void return type
                    PrimitiveParameter primitiveParameter = new PrimitiveParameter("return", DirectionEnum.Return, TypeEnum.Void);
                    theOperation.addParameter(primitiveParameter);

                    // Process regular parameters of the function
                    for (ParameterSpecification theParameter : theProcedure.getParameterSpecifications()) {
                        String parameterName = theParameter.getName();
                        String parameterMode = theParameter.getMode();
                        DirectionEnum directionEnum = convertToDirectionEnum(parameterMode);
                        String parameterType = theParameter.getType();

                        if (isPrimitive(parameterType)) {
                            TypeEnum typeEnum = convertToTypeEnum(parameterType);

                            Object parameterDefaultValue = null;

                            if (theParameter.getInitializationExpressionQ() != null) {
                                switch (typeEnum) {
                                    case Integer:
                                        if (theParameter.getInitializationExpressionQ().getIntegerLiteral() != null)
                                            parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                        break;
                                    case Boolean:
                                        if (theParameter.getInitializationExpressionQ().getEnumerationLiteral() != null)
                                            parameterDefaultValue = Boolean.parseBoolean(theParameter.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                        break;
                                    case Real:
                                        if (theParameter.getInitializationExpressionQ().getRealLiteral() != null)
                                            parameterDefaultValue = Double.parseDouble(theParameter.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                        break;
                                    case UnlimitedNatural:
                                        if (theParameter.getInitializationExpressionQ().getIntegerLiteral() != null)
                                            parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                        break;
                                    case String:
                                        if (theParameter.getInitializationExpressionQ().getStringLiteral() != null)
                                            parameterDefaultValue = theParameter.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                        break;
                                    default:
                                        parameterDefaultValue = null;
                                }
                            }

                            primitiveParameter = new PrimitiveParameter(parameterName, directionEnum, typeEnum, parameterDefaultValue);
                            theOperation.addParameter(primitiveParameter);
                        } else {
                            ClassParameter classParameter = new ClassParameter(parameterName, directionEnum, parameterType);
                            theOperation.addParameter(classParameter);
                        }
                    }

                    // IDENTIFY WHERE TO PUT THIS OPERATION

                    // If there is only 1 parameter, it is just return.
                    // Should be put to the class named after the package
                    if (theOperation.getParameters().size() == 1) {
                        Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                        classNamedAfterAdaPackage.addOperation(theOperation);
                    }

                    // If there are more than 1 parameter, let's check the type of the first parameter.
                    // first parameter is the one after return parameter, so index is 1
                    if (theOperation.getParameters().size() > 1) {
                        Parameter firstParameter = theOperation.getParameters().get(1);

                        // If first parameter is primitive, then it is like no parameter than return.
                        // Else, fix the class of the first parameter and put it to that class as an operation
                        //      If type couldn't be fixed, then, put it to the classNamedAfterAdaPackage again
                        if (firstParameter instanceof PrimitiveParameter) {
                            Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                            classNamedAfterAdaPackage.addOperation(theOperation);
                        } else {
                            ClassParameter castedParameter = ((ClassParameter) firstParameter);
                            for (Class aClass : resultingUML.collectAllClasses()) {
                                if (!castedParameter.getPlaceholder().contains(".") && aClass.getName().equals(castedParameter.getPlaceholder())) {
                                    castedParameter.fixType(aClass);
                                }
                            }
                            if (castedParameter.getType() != null)
                                castedParameter.getType().addOperation(theOperation);
                            else {
                                Class classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(classNameAfterPackageName);
                                classNamedAfterAdaPackage.addOperation(theOperation);
                            }
                        }
                    }
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }

        }

        // Try to fix the local placeholders because if they don't have . in their place holder, probably these classes are in the same UML.
        resultingUML.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        if (!exceptions.isEmpty())
            throw new PartialUMLException("This UML has thrown some exceptions!", resultingUML, exceptions);

        return resultingUML;
    }

    public static boolean isPrimitive(String type) {
        return type.equals("Integer")
                || type.equals("String")
                || type.equals("Boolean")
                || type.equals("UnlimitedNatural")
                || type.equals("Natural")
                || type.equals("Real")
                || type.equals("Float")
                || type.equals("Void")
                || type.equals("Positive");
    }

    public static TypeEnum convertToTypeEnum(String type) {
        if (type.equals("Natural"))
            return TypeEnum.UnlimitedNatural;
        else if (type.equals("Float"))
            return TypeEnum.Real;
        else if (type.equals("Positive"))
            return TypeEnum.Integer;
        else
            return TypeEnum.valueOf(type);
    }

    public static DirectionEnum convertToDirectionEnum(String direction) {
        switch (direction) {
            case "AN_IN_MODE":
                return DirectionEnum.In;
            case "AN_OUT_MODE":
                return DirectionEnum.Out;
            case "AN_IN_OUT_MODE":
                return DirectionEnum.InOut;
            default:
                return DirectionEnum.In;
        }
    }

}

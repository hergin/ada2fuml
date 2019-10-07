package extractor;

import adaschema.CompilationUnit;
import exceptions.NamingException;
import exceptions.UnhandledTypeException;
import exceptions.UnknownTypeException;
import model.Class;
import model.Operation;
import model.Package;
import model.UML;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.parameters.ClassParameter;
import model.parameters.PrimitiveParameter;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;

import java.util.Arrays;

public class Extractor {

    public static UML extractHighLevelConcepts(CompilationUnit compilationUnit) throws UnhandledTypeException, NamingException, UnknownTypeException {
        UML resultingUML = new UML(compilationUnit.getDefName(),compilationUnit.getSourceFile());

        if (compilationUnit.getUnitDeclarationQ().getPackageDeclaration()!=null) {

            var thePackage = compilationUnit.getUnitDeclarationQ().getPackageDeclaration();
            var packageName = thePackage.getName();

            for (var theType : thePackage.getOrdinaryTypes()) {
                var typeName = theType.getName();

                Class theClass = null;

                // packageX + typeX => classX
                if (packageName.equals(typeName)) {
                    theClass = resultingUML.createOrGetClassByName(typeName);
                }

                // packageX + typeY => packageX + classY
                if (!packageName.equals(typeName)) {
                    Package packageNamedAfterAdaPackage = resultingUML.createOrGetPackageByName(packageName);

                    theClass = packageNamedAfterAdaPackage.createOrGetClassByName(typeName);
                }

                // TODO package name with . in it means there is a subpackage.

                // TODO ordinaryTypes with an extension, means there is a superclass involved. See @RoyTests.md_example4-nest test

                if(theClass!=null) {
                    var components = theType.getComponentDeclarations();

                    if(components.isEmpty() && false) {
                        // TODO this means it is an unhandled type in TypeDeclarationViewQ.
                        //      Class will be in the resulting UML but will probably be empty.
                        //      These are still OrdinaryTypeDeclarations, so to fill the details one might need to go into more details.
                        var classDef = theType.getTypeDeclarationViewQ().getClass();
                        var fieldDefs = classDef.getDeclaredFields();

                        for(var fieldDef:fieldDefs) {
                            Object value = null;
                            fieldDef.setAccessible(true);
                            try {
                                value = fieldDef.get(theType.getTypeDeclarationViewQ());
                            } catch (IllegalAccessException e) {
                                //e.printStackTrace();
                            }
                            if(value!=null) {
                                if(!fieldDef.getName().equals("recordTypeDefinition")
                                    && !fieldDef.getName().equals("taggedRecordTypeDefinition")) {
                                    throw new UnhandledTypeException("An unhandled type is found in TypeDeclarationViewQ: "+fieldDef.getName());
                                }
                            }
                        }

                    }

                    for(var component : components) {
                        var name = component.getName();
                        var type = component.getType();

                        //var visibility = null; // TODO FIND IT

                        if(isPrimitive(type)) {
                            var typeEnum = convertToTypeEnum(type);

                            Object defaultValue = null;

                            if(component.getInitializationExpressionQ()!=null) {
                                switch (typeEnum) {
                                    case Integer:
                                        if(component.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                            defaultValue = Integer.parseInt(component.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                        break;
                                    case Boolean:
                                        if(component.getInitializationExpressionQ().getEnumerationLiteral()!=null)
                                            defaultValue = Boolean.parseBoolean(component.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                        break;
                                    case Real:
                                        if(component.getInitializationExpressionQ().getRealLiteral()!=null)
                                            defaultValue = Double.parseDouble(component.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                        break;
                                    case UnlimitedNatural:
                                        if(component.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                            defaultValue = Integer.parseInt(component.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                        break;
                                    case String:
                                        if(component.getInitializationExpressionQ().getStringLiteral()!=null)
                                            defaultValue = component.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                        break;
                                    default:
                                        defaultValue = null;
                                }
                            }

                            var primitiveProperty = new PrimitiveProperty(name, VisibilityEnum.Public,typeEnum,defaultValue);
                            theClass.addProperty(primitiveProperty);
                        } else {
                            var classProperty = new ClassProperty(name,VisibilityEnum.Public,type);
                            theClass.addProperty(classProperty);
                        }
                    }
                }
            }

            for (var theVariable : thePackage.getVariableDeclarations()) {
                var variableName = theVariable.getName();
                var variableType = theVariable.getType();

                //var variableVisibility = null; // TODO FIND IT

                if(isPrimitive(variableType)) {
                    var typeEnum = convertToTypeEnum(variableType);

                    Object variableDefaultValue = null;

                    if(theVariable.getInitializationExpressionQ()!=null) {
                        switch (typeEnum) {
                            case Integer:
                                if(theVariable.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                    variableDefaultValue = Integer.parseInt(theVariable.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                break;
                            case Boolean:
                                if(theVariable.getInitializationExpressionQ().getEnumerationLiteral()!=null)
                                    variableDefaultValue = Boolean.parseBoolean(theVariable.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                break;
                            case Real:
                                if(theVariable.getInitializationExpressionQ().getRealLiteral()!=null)
                                    variableDefaultValue = Double.parseDouble(theVariable.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                break;
                            case UnlimitedNatural:
                                if(theVariable.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                    variableDefaultValue = Integer.parseInt(theVariable.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                break;
                            case String:
                                if(theVariable.getInitializationExpressionQ().getStringLiteral()!=null)
                                    variableDefaultValue = theVariable.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                break;
                            default:
                                variableDefaultValue = null;
                        }
                    }

                    var primitiveProperty = new PrimitiveProperty(variableName,VisibilityEnum.Public,typeEnum,variableDefaultValue);
                    // Put the variable without any type to a class same named with the package
                    var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                    classNamedAfterAdaPackage.addProperty(primitiveProperty);
                } else {
                    var classProperty = new ClassProperty(variableName,VisibilityEnum.Public,variableType);
                    // Put the variable without any type to a class same named with the package
                    var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                    classNamedAfterAdaPackage.addProperty(classProperty);
                }
            }

            for (var theFunction : thePackage.getFunctionDeclarations()) {
                var functionName = theFunction.getName();
                var returnType = theFunction.getReturnType();

                var theOperation = new Operation(functionName,VisibilityEnum.Public);

                // Identify return type and add it as parameter
                if(isPrimitive(returnType)) {
                    var typeEnum = convertToTypeEnum(returnType);
                    var primitiveParameter = new PrimitiveParameter("return", DirectionEnum.Return,typeEnum);
                    theOperation.addParameter(primitiveParameter);
                } else {
                    var classParameter = new ClassParameter(functionName+"_Return",DirectionEnum.Return,returnType);
                    theOperation.addParameter(classParameter);
                }

                // Process regular parameters of the function
                for (var theParameter:theFunction.getParameterSpecifications()) {
                    var parameterName = theParameter.getName();
                    var parameterMode = theParameter.getMode();
                    var directionEnum = convertToDirectionEnum(parameterMode);
                    var parameterType = theParameter.getType();

                    if(isPrimitive(parameterType)) {
                        var typeEnum = convertToTypeEnum(parameterType);

                        Object parameterDefaultValue = null;

                        if(theParameter.getInitializationExpressionQ()!=null) {
                            switch (typeEnum) {
                                case Integer:
                                    if(theParameter.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                        parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                    break;
                                case Boolean:
                                    if(theParameter.getInitializationExpressionQ().getEnumerationLiteral()!=null)
                                        parameterDefaultValue = Boolean.parseBoolean(theParameter.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                    break;
                                case Real:
                                    if(theParameter.getInitializationExpressionQ().getRealLiteral()!=null)
                                        parameterDefaultValue = Double.parseDouble(theParameter.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                    break;
                                case UnlimitedNatural:
                                    if(theParameter.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                        parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                    break;
                                case String:
                                    if(theParameter.getInitializationExpressionQ().getStringLiteral()!=null)
                                        parameterDefaultValue = theParameter.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                    break;
                                default:
                                    parameterDefaultValue = null;
                            }
                        }

                        var primitiveParameter = new PrimitiveParameter(parameterName,directionEnum,typeEnum,parameterDefaultValue);
                        theOperation.addParameter(primitiveParameter);
                    } else {
                        var classParameter = new ClassParameter(parameterName,directionEnum,parameterType);
                        theOperation.addParameter(classParameter);
                    }
                }

                // IDENTIFY WHERE TO PUT THIS OPERATION

                // If there is only 1 parameter, it is just return.
                // Should be put to the class named after the package
                if(theOperation.getParameters().size()==1) {
                    var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                    classNamedAfterAdaPackage.addOperation(theOperation);
                }

                // If there are more than 1 parameter, let's check the type of the first parameter.
                // first parameter is the one after return parameter, so index is 1
                if(theOperation.getParameters().size()>1) {
                    var firstParameter = theOperation.getParameters().get(1);

                    // If first parameter is primitive, then it is like no parameter than return.
                    // Else, fix the class of the first parameter and put it to that class as an operation
                    //      If type couldn't be fixed, then, put it to the classNamedAfterAdaPackage again
                    if(firstParameter instanceof PrimitiveParameter) {
                        var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                        classNamedAfterAdaPackage.addOperation(theOperation);
                    } else {
                        var castedParameter = ((ClassParameter) firstParameter);
                        for (var aClass:resultingUML.getClasses()) {
                            if(aClass.getName().equals(castedParameter.getPlaceholder())) {
                                castedParameter.fixType(aClass);
                            }
                        }
                        if(castedParameter.getType()==null) {
                            for (var aPackage : resultingUML.getPackages()) {
                                for (var theClass : aPackage.getClasses()) {
                                    if (theClass.getName().equals(castedParameter.getPlaceholder())) {
                                        castedParameter.fixType(theClass);
                                    }
                                }
                            }
                        }
                        if(castedParameter.getType()!=null)
                            castedParameter.getType().addOperation(theOperation);
                        else {
                            var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                            classNamedAfterAdaPackage.addOperation(theOperation);
                        }
                    }
                }

            }


            for (var theProcedure : thePackage.getProcedureDeclarations()) {
                var functionName = theProcedure.getName();

                var theOperation = new Operation(functionName,VisibilityEnum.Public);

                // void return type
                var primitiveParameter = new PrimitiveParameter("return", DirectionEnum.Return,TypeEnum.Void);
                theOperation.addParameter(primitiveParameter);

                // Process regular parameters of the function
                for (var theParameter:theProcedure.getParameterSpecifications()) {
                    var parameterName = theParameter.getName();
                    var parameterMode = theParameter.getMode();
                    var directionEnum = convertToDirectionEnum(parameterMode);
                    var parameterType = theParameter.getType();

                    if(isPrimitive(parameterType)) {
                        var typeEnum = convertToTypeEnum(parameterType);

                        Object parameterDefaultValue = null;

                        if(theParameter.getInitializationExpressionQ()!=null) {
                            switch (typeEnum) {
                                case Integer:
                                    if(theParameter.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                        parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                    break;
                                case Boolean:
                                    if(theParameter.getInitializationExpressionQ().getEnumerationLiteral()!=null)
                                        parameterDefaultValue = Boolean.parseBoolean(theParameter.getInitializationExpressionQ().getEnumerationLiteral().getRefName().toLowerCase());
                                    break;
                                case Real:
                                    if(theParameter.getInitializationExpressionQ().getRealLiteral()!=null)
                                        parameterDefaultValue = Double.parseDouble(theParameter.getInitializationExpressionQ().getRealLiteral().getLitVal());
                                    break;
                                case UnlimitedNatural:
                                    if(theParameter.getInitializationExpressionQ().getIntegerLiteral()!=null)
                                        parameterDefaultValue = Integer.parseInt(theParameter.getInitializationExpressionQ().getIntegerLiteral().getLitVal());
                                    break;
                                case String:
                                    if(theParameter.getInitializationExpressionQ().getStringLiteral()!=null)
                                        parameterDefaultValue = theParameter.getInitializationExpressionQ().getStringLiteral().getLitVal();
                                    break;
                                default:
                                    parameterDefaultValue = null;
                            }
                        }

                        primitiveParameter = new PrimitiveParameter(parameterName,directionEnum,typeEnum,parameterDefaultValue);
                        theOperation.addParameter(primitiveParameter);
                    } else {
                        var classParameter = new ClassParameter(parameterName,directionEnum,parameterType);
                        theOperation.addParameter(classParameter);
                    }
                }

                // IDENTIFY WHERE TO PUT THIS OPERATION

                // If there is only 1 parameter, it is just return.
                // Should be put to the class named after the package
                if(theOperation.getParameters().size()==1) {
                    var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                    classNamedAfterAdaPackage.addOperation(theOperation);
                }

                // If there are more than 1 parameter, let's check the type of the first parameter.
                // first parameter is the one after return parameter, so index is 1
                if(theOperation.getParameters().size()>1) {
                    var firstParameter = theOperation.getParameters().get(1);

                    // If first parameter is primitive, then it is like no parameter than return.
                    // Else, fix the class of the first parameter and put it to that class as an operation
                    //      If type couldn't be fixed, then, put it to the classNamedAfterAdaPackage again
                    if(firstParameter instanceof PrimitiveParameter) {
                        var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                        classNamedAfterAdaPackage.addOperation(theOperation);
                    } else {
                        var castedParameter = ((ClassParameter) firstParameter);
                        for (var aClass:resultingUML.getClasses()) {
                            if(aClass.getName().equals(castedParameter.getPlaceholder())) {
                                castedParameter.fixType(aClass);
                            }
                        }
                        if(castedParameter.getType()==null) {
                            for (var aPackage : resultingUML.getPackages()) {
                                for (var theClass : aPackage.getClasses()) {
                                    if (theClass.getName().equals(castedParameter.getPlaceholder())) {
                                        castedParameter.fixType(theClass);
                                    }
                                }
                            }
                        }
                        if(castedParameter.getType()!=null)
                            castedParameter.getType().addOperation(theOperation);
                        else {
                            var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                            classNamedAfterAdaPackage.addOperation(theOperation);
                        }
                    }
                }

            }

        }

        // Try to fix the local placeholders because if they don't have . in their place holder, probably these classes are in the same UML.
        resultingUML.replaceLocalPlaceholders();

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
                || type.equals("Void");
    }

    public static TypeEnum convertToTypeEnum(String type) {
        if(type.equals("Natural"))
            return TypeEnum.UnlimitedNatural;
        else if(type.equals("Float"))
            return TypeEnum.Real;
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

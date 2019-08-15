package extractor;

import adaschema.CompilationUnit;
import model.Class;
import model.Package;
import model.UML;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;

public class Extractor {

    public static UML extractHighLevelConcepts(CompilationUnit compilationUnit) {
        UML resultingUML = new UML(compilationUnit.getUnitFullName());

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


                if(theClass!=null) {
                    var components = theType.getComponentDeclarations();

                    for(var component : components) {
                        var name = component.getName();
                        var type = component.getType();
                        //var defaultValue = null; // TODO FIND IT
                        //var visibility = null; // TODO FIND IT

                        if(isPrimitive(type)) {
                            var typeEnum = convertToTypeEnum(type);
                            var primitiveProperty = new PrimitiveProperty(name, VisibilityEnum.Public,typeEnum,null);
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
                //var variableDefaultValue = null; // TODO FIND IT
                //var variableVisibility = null; // TODO FIND IT

                if(isPrimitive(variableType)) {
                    var typeEnum = convertToTypeEnum(variableType);
                    var primitiveProperty = new PrimitiveProperty(variableName,VisibilityEnum.Public,typeEnum,null);
                    var classNamedAfterAdaPackage = resultingUML.createOrGetClassByName(packageName);
                    classNamedAfterAdaPackage.addProperty(primitiveProperty);
                } else {
                    // TODO class variable
                }
            }

            for (var theFunction : thePackage.getFunctionDeclarations()) {
                //var functionName =
            }

        }

        return resultingUML;
    }

    public static boolean isPrimitive(String type) {
        return type.equals("Integer")
                || type.equals("String")
                || type.equals("Boolean")
                || type.equals("UnlimitedNatural")
                || type.equals("Real")
                || type.equals("Void");
    }

    public static TypeEnum convertToTypeEnum(String type) {
        return TypeEnum.valueOf(type);
    }

}

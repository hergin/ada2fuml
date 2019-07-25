package extractor;

import adaschema.CompilationUnit;
import model.Class;
import model.Package;
import model.UML;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
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
                        }
                    }
                }
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

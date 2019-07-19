package extractor;

import adaschema.CompilationUnit;
import model.Class;
import model.Package;
import model.UML;

public class Extractor {

    public static UML extractHighLevelConcepts(CompilationUnit compilationUnit) {
        UML resultingUML = new UML(compilationUnit.getUnitFullName());

        var thePackage = compilationUnit.getUnitDeclarationQ().getPackageDeclaration();
        var packageName = thePackage.getName();

        for (var theType:thePackage.getOrdinaryTypes()) {
            var typeName = theType.getName();

            // packageX + typeX => classX
            if(packageName.equals(typeName)) {
                Class sameNamedClass = null;
                if(resultingUML.hasClass(typeName)) {
                    sameNamedClass = resultingUML.getClassByName(typeName);
                } else {
                    sameNamedClass = new Class(typeName);
                    resultingUML.addClass(sameNamedClass);
                }
            }

            // packageX + typeY => packageX + classY
            if(!packageName.equals(typeName)) {
                Package packageNamedAfterAdaPackage = null;
                if(resultingUML.hasPackage(packageName)) {
                    packageNamedAfterAdaPackage = resultingUML.getPackageByName(packageName);
                } else {
                    packageNamedAfterAdaPackage = new Package(packageName);
                    resultingUML.addPackage(packageNamedAfterAdaPackage);
                }

                Class typeNamedClass = null;
                if(packageNamedAfterAdaPackage.hasClass(typeName)) {
                    typeNamedClass = packageNamedAfterAdaPackage.getClassByName(typeName);
                } else {
                    typeNamedClass = new Class(typeName);
                    packageNamedAfterAdaPackage.addClass(typeNamedClass);
                }
            }
        }

        return resultingUML;
    }

}

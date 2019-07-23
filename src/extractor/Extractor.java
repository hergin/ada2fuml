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
                resultingUML.createOrGetClassByName(typeName);
            }

            // packageX + typeY => packageX + classY
            if(!packageName.equals(typeName)) {
                Package packageNamedAfterAdaPackage = resultingUML.createOrGetPackageByName(packageName);

                Class typeNamedClass = packageNamedAfterAdaPackage.createOrGetClassByName(typeName);
            }
        }

        return resultingUML;
    }

}

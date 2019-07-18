package extractor;

import adaschema.CompilationUnit;
import model.Class;
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
                Class sameNamedClass = new Class(typeName);
                resultingUML.addClass(sameNamedClass);
            }
        }

        return resultingUML;
    }

}

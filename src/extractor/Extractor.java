package extractor;

import adaschema.CompilationUnit;
import model.UML;

public class Extractor {

    public static UML extractHighLevelConcepts(CompilationUnit compilationUnit) {
        UML resultingUML = new UML(compilationUnit.getUnitFullName());



        return resultingUML;
    }

}

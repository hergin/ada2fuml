package Integration;

import adaschema.CompilationUnit;
import exceptions.Gnat2XmlException;
import exceptions.NamingException;
import exceptions.PartialUMLException;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.UML;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class SubTypeFromRoyTests {

    @Test
    void test_gm_text_types_1() throws Gnat2XmlException, JAXBException, NamingException, URISyntaxException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/gm_text_types.1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);

        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1, resultUml.getPackages().size());
        Assertions.assertEquals(4, resultUml.getPackages().get(0).getClasses().size());

        Assertions.assertEquals("Name_Length_Type", resultUml.getPackages().get(0).getClasses().get(0).getName());
        Assertions.assertEquals(1, resultUml.getPackages().get(0).getClasses().get(0).getSuperClasses().size());
        Assertions.assertEquals("Positive", resultUml.getPackages().get(0).getClasses().get(0).getSuperClasses().get(0).getName());

        Assertions.assertEquals("Positive", resultUml.getPackages().get(0).getClasses().get(1).getName());

        Assertions.assertEquals("Name_Type", resultUml.getPackages().get(0).getClasses().get(2).getName());
        Assertions.assertEquals(1, resultUml.getPackages().get(0).getClasses().get(2).getSuperClasses().size());
        Assertions.assertEquals("String", resultUml.getPackages().get(0).getClasses().get(2).getSuperClasses().get(0).getName());

        Assertions.assertEquals("String", resultUml.getPackages().get(0).getClasses().get(3).getName());
    }

    @Test
    void test_gm_unit_rank_types_1() throws Gnat2XmlException, JAXBException, URISyntaxException, PartialUMLException, NamingException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/gm_unit_rank_types.1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);

        try {
            UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);
        } catch (PartialUMLException pmu) {
            UML partialUml = pmu.getPartialUML();

            Assertions.assertEquals(1, partialUml.getPackages().size());
            Assertions.assertEquals(6, partialUml.getPackages().get(0).getClasses().size());

            Assertions.assertEquals("Extended_Numeric_Rank_Type", partialUml.getPackages().get(0).getClasses().get(0).getName());
            Assertions.assertEquals(1, partialUml.getPackages().get(0).getClasses().get(0).getSuperClasses().size());
            Assertions.assertEquals("Integer", partialUml.getPackages().get(0).getClasses().get(0).getSuperClasses().get(0).getName());

            Assertions.assertEquals("Integer", partialUml.getPackages().get(0).getClasses().get(1).getName());

            Assertions.assertEquals("Numeric_Rank_Type", partialUml.getPackages().get(0).getClasses().get(2).getName());
            Assertions.assertEquals(1, partialUml.getPackages().get(0).getClasses().get(2).getSuperClasses().size());
            Assertions.assertEquals("Extended_Numeric_Rank_Type", partialUml.getPackages().get(0).getClasses().get(2).getSuperClasses().get(0).getName());

            Assertions.assertEquals("Rank_Type", partialUml.getPackages().get(0).getClasses().get(3).getName());

            Assertions.assertEquals("Unit_Numeric_Rank_Type", partialUml.getPackages().get(0).getClasses().get(4).getName());

            Assertions.assertEquals("Unit_Rank_Type", partialUml.getPackages().get(0).getClasses().get(5).getName());
        }
    }
}

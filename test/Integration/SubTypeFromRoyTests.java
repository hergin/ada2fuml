package Integration;

import adaschema.CompilationUnit;
import exceptions.*;
import exporter.Processor;
import exporter.StillHavePlaceholderExceptionPolicy;
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
    void test_gm_unit_rank_types_1() throws Gnat2XmlException, JAXBException, URISyntaxException, PartialUMLException, NamingException, UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/gm_unit_rank_types.1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);

        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);


        Assertions.assertEquals(1, resultUml.getPackages().size());
        Assertions.assertEquals(5, resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(1, resultUml.getPackages().get(0).getEnumerations().size());

        Assertions.assertEquals("Extended_Numeric_Rank_Type", resultUml.getPackages().get(0).getClasses().get(0).getName());
        Assertions.assertEquals(1, resultUml.getPackages().get(0).getClasses().get(0).getSuperClasses().size());
        Assertions.assertEquals("Integer", resultUml.getPackages().get(0).getClasses().get(0).getSuperClasses().get(0).getName());

        Assertions.assertEquals("Integer", resultUml.getPackages().get(0).getClasses().get(1).getName());

        Assertions.assertEquals("Numeric_Rank_Type", resultUml.getPackages().get(0).getClasses().get(2).getName());
        Assertions.assertEquals(1, resultUml.getPackages().get(0).getClasses().get(2).getSuperClasses().size());
        Assertions.assertEquals("Extended_Numeric_Rank_Type", resultUml.getPackages().get(0).getClasses().get(2).getSuperClasses().get(0).getName());

        Assertions.assertEquals("Unit_Numeric_Rank_Type", resultUml.getPackages().get(0).getClasses().get(3).getName());

        Assertions.assertEquals("Unit_Rank_Type", resultUml.getPackages().get(0).getClasses().get(4).getName());

        Assertions.assertEquals("Rank_Type", resultUml.getPackages().get(0).getEnumerations().get(0).getName());
        Assertions.assertEquals(103, resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().size());
        Assertions.assertEquals("Unknown", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(0).getName());
        Assertions.assertEquals("Thirteen", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(16).getName());
        Assertions.assertEquals("Forty_One", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(44).getName());
        Assertions.assertEquals("Ninety_Nine", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(102).getName());

        String result = Processor.processUML(resultUml, StillHavePlaceholderExceptionPolicy.Throw);
        int a =1;
    }
}

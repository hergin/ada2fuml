package Integration;

import adaschema.CompilationUnit;
import exceptions.*;
import exporter.Processor;
import exporter.StillHavePlaceholderExceptionPolicy;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.Property;
import model.UML;
import model.enums.TypeEnum;
import model.properties.CustomPrimitiveProperty;
import model.properties.EnumerationProperty;
import model.properties.PrimitiveProperty;
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
        Assertions.assertEquals(0, resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(2, resultUml.getPackages().get(0).getCustomPrimitives().size());

        Assertions.assertEquals("Name_Length_Type", resultUml.getPackages().get(0).getCustomPrimitives().get(0).getName());
        Assertions.assertEquals(TypeEnum.Integer, resultUml.getPackages().get(0).getCustomPrimitives().get(0).getSuperPrimitive());

        Assertions.assertEquals("Name_Type", resultUml.getPackages().get(0).getCustomPrimitives().get(1).getName());
        Assertions.assertEquals(TypeEnum.String, resultUml.getPackages().get(0).getCustomPrimitives().get(1).getSuperPrimitive());
        Assertions.assertEquals(0, resultUml.getPackages().get(0).getCustomPrimitives().get(1).getSuperCustomPrimitives().size());
    }

    @Test
    void test_gm_unit_rank_types_1() throws Gnat2XmlException, JAXBException, URISyntaxException, PartialUMLException, NamingException, UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/gm_unit_rank_types.1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);

        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);


        Assertions.assertEquals(1, resultUml.getPackages().size());
        Assertions.assertEquals(2, resultUml.getPackages().get(0).getCustomPrimitives().size());

        Assertions.assertEquals("Extended_Numeric_Rank_Type", resultUml.getPackages().get(0).getCustomPrimitives().get(0).getName());
        Assertions.assertEquals(TypeEnum.Integer, resultUml.getPackages().get(0).getCustomPrimitives().get(0).getSuperPrimitive());
        Assertions.assertEquals(0, resultUml.getPackages().get(0).getCustomPrimitives().get(0).getSuperCustomPrimitives().size());

        Assertions.assertEquals("Numeric_Rank_Type", resultUml.getPackages().get(0).getCustomPrimitives().get(1).getName());
        Assertions.assertNull(resultUml.getPackages().get(0).getCustomPrimitives().get(1).getSuperPrimitive());
        Assertions.assertEquals(1, resultUml.getPackages().get(0).getCustomPrimitives().get(1).getSuperCustomPrimitives().size());
        Assertions.assertEquals("Extended_Numeric_Rank_Type", resultUml.getPackages().get(0).getCustomPrimitives().get(1).getSuperCustomPrimitives().get(0).getName());

        Assertions.assertEquals(1, resultUml.getPackages().get(0).getEnumerations().size());
        Assertions.assertEquals("Rank_Type", resultUml.getPackages().get(0).getEnumerations().get(0).getName());
        Assertions.assertEquals(103, resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().size());
        Assertions.assertEquals("Unknown", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(0).getName());
        Assertions.assertEquals("Thirteen", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(16).getName());
        Assertions.assertEquals("Forty_One", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(44).getName());
        Assertions.assertEquals("Ninety_Nine", resultUml.getPackages().get(0).getEnumerations().get(0).getLiterals().get(102).getName());


        Assertions.assertEquals(2, resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals("Unit_Numeric_Rank_Type", resultUml.getPackages().get(0).getClasses().get(0).getName());
        Assertions.assertEquals(2, resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        Assertions.assertEquals(PrimitiveProperty.class, resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(0).getClass());
        Assertions.assertEquals("Unit", resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(0).getName());
        Assertions.assertEquals(CustomPrimitiveProperty.class, resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(1).getClass());
        Assertions.assertEquals("Rank", resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(1).getName());
        Assertions.assertEquals("Numeric_Rank_Type", ((CustomPrimitiveProperty) resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(1)).getType().getName());

        Assertions.assertEquals("Unit_Rank_Type", resultUml.getPackages().get(0).getClasses().get(1).getName());
        Assertions.assertEquals(2, resultUml.getPackages().get(0).getClasses().get(1).getProperties().size());
        Assertions.assertEquals(PrimitiveProperty.class, resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(0).getClass());
        Assertions.assertEquals("Unit", resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(0).getName());
        Assertions.assertEquals(EnumerationProperty.class, resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(1).getClass());
        Assertions.assertEquals("Rank", resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(1).getName());
        Assertions.assertEquals("Rank_Type", ((EnumerationProperty) resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(1)).getType().getName());
    }
}

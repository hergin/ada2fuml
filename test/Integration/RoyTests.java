package Integration;

import adaschema.CompilationUnit;
import exceptions.*;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import gnat2xml.Gnat2XmlRunnerTests;
import model.Parameter;
import model.Property;
import model.UML;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;
import model.parameters.ClassParameter;
import model.parameters.PrimitiveParameter;
import model.properties.ClassProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class RoyTests {

    @Test
    void test_globals_example1() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/globals_example1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        try {
            UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);
            // TODO check other stuff in UML. This UML is success.
        } catch (PartialUMLException pmu) {
            UML resultUml = pmu.getPartialUML();
            Assertions.assertEquals(1, resultUml.getPackages().size());
        }
    }

    @Test
    void test_globals_example2() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/globals_example2.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_globals_example3() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/globals_example3.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example1() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getClasses().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example2() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example2.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example3() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example3.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(1,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example4() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example4.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(1,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example4_nested() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example4-nested.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getSubPackages().size());
        Assertions.assertEquals(0,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getSubPackages().get(0).getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getSubPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getSubPackages().get(0).getClasses().get(0).getProperties().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getSubPackages().get(0).getClasses().get(0).getSuperClasses().size());
        Assertions.assertEquals("T",resultUml.getPackages().get(0).getSubPackages().get(0).getClasses().get(0).getSuperClasses().get(0).getName());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example5() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example5.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getSubPackages().size());
        Assertions.assertEquals(0,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example6() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example6.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getSubPackages().size());
        Assertions.assertEquals(0,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(2,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());

        Assertions.assertEquals("Do_It",resultUml.getPackages().get(0).getClasses().get(0).getOperations().get(0).getName());
        Assertions.assertEquals("Do_It_Return",resultUml.getPackages().get(0).getClasses().get(0).getOperations().get(0).getParameters().get(0).getName());
        Assertions.assertEquals(ClassParameter.class,resultUml.getPackages().get(0).getClasses().get(0).getOperations().get(0).getParameters().get(0).getClass());

        Assertions.assertEquals(DirectionEnum.Return,resultUml.getPackages().get(0).getClasses().get(0).getOperations().get(1).getParameters().get(0).getDirection());
        Assertions.assertEquals(TypeEnum.Void, ((PrimitiveParameter) resultUml.getPackages().get(0).getClasses().get(0).getOperations().get(1).getParameters().get(0)).getType());

        Assertions.assertEquals(DirectionEnum.InOut, ((ClassParameter) resultUml.getPackages().get(0).getClasses().get(0).getOperations().get(1).getParameters().get(1)).getDirection());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example7() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example7.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getSubPackages().size());
        Assertions.assertEquals(0,resultUml.getClasses().size());
        Assertions.assertEquals(3,resultUml.getPackages().get(0).getClasses().size());

        Assertions.assertEquals("T",resultUml.getPackages().get(0).getClasses().get(0).getName());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getClasses().get(0).getSuperClasses().size());
        Assertions.assertEquals("Globals_Example1.Itype", ((ClassProperty) resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(0)).getPlaceholder());
        Assertions.assertEquals("Attribute", ((ClassProperty) resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(0)).getName());

        Assertions.assertEquals("T2",resultUml.getPackages().get(0).getClasses().get(1).getName());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(1).getProperties().size());
        Assertions.assertEquals("Globals_Example1.Itype", ((ClassProperty) resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(0)).getPlaceholder());
        Assertions.assertEquals("Child_Attribute", ((ClassProperty) resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(0)).getName());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(1).getSuperClasses().size());
        Assertions.assertEquals("T",resultUml.getPackages().get(0).getClasses().get(1).getSuperClasses().get(0).getName());

        Assertions.assertEquals("T3",resultUml.getPackages().get(0).getClasses().get(2).getName());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getClasses().get(2).getProperties().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(2).getSuperClasses().size());
        Assertions.assertEquals("T2",resultUml.getPackages().get(0).getClasses().get(2).getSuperClasses().get(0).getName());
        // TODO check other stuff in UML. This UML has placeholders.
    }

}

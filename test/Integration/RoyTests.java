package Integration;

import exceptions.Gnat2XmlException;
import exceptions.NamingException;
import exceptions.UnhandledTypeException;
import exceptions.UnknownTypeException;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import gnat2xml.Gnat2XmlRunnerTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class RoyTests {

    @Test
    void test_globals_example1() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/globals_example1.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        // TODO check other stuff in UML. This UML is success.
    }

    @Test
    void test_globals_example2() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/globals_example2.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_globals_example3() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/globals_example3.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example1() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example1.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getClasses().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example2() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example2.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example3() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example3.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(1,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example4() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example4.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(1,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(0,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

    @Test
    void test_md_example4_nested() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("ExamplesFromRoy/md_example4-nested.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals(0,resultUml.getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getOperations().size());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        // TODO check other stuff in UML. This UML has placeholders.
    }

}

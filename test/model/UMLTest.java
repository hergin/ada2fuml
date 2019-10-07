package model;

import Integration.RoyTests;
import exceptions.*;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.enums.PlaceholderPreferenceEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class UMLTest {

    @Test
    void test_fixGlobalPlaceholders() throws Gnat2XmlException, JAXBException, UnknownTypeException, NamingException, UnhandledTypeException, URISyntaxException, PartialUMLException {
        var adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("SimpleInterFileAssociation/Class1.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var class1uml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertFalse(class1uml.hasPlaceholders());

        adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("SimpleInterFileAssociation/Class2.ads").toURI()).toFile();
        adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var class2uml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertTrue(class2uml.hasPlaceholders());

        var combinedUML = new UML("combined");
        combinedUML.combine(class1uml);
        combinedUML.combine(class2uml);

        Assertions.assertTrue(combinedUML.hasPlaceholders());

        combinedUML.fixPlaceholders(PlaceholderPreferenceEnum.Global);

        Assertions.assertFalse(combinedUML.hasPlaceholders());
    }

}

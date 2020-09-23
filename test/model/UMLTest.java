package model;

import Integration.RoyTests;
import adaschema.CompilationUnit;
import exceptions.*;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.enums.PlaceholderPreferenceEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class UMLTest {

    @Test
    void test_fixGlobalPlaceholders() throws Gnat2XmlException, JAXBException, UnknownTypeException, NamingException, UnhandledTypeException, URISyntaxException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("SimpleInterFileAssociation/Class1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML class1uml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertFalse(class1uml.hasPlaceholders());

        adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("SimpleInterFileAssociation/Class2.ads").toURI()).toFile();
        adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML class2uml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertTrue(class2uml.hasPlaceholders());

        UML combinedUML = new UML("combined");
        combinedUML.combine(class1uml);
        combinedUML.combine(class2uml);

        Assertions.assertTrue(combinedUML.hasPlaceholders());

        combinedUML.fixPlaceholders(PlaceholderPreferenceEnum.Global);

        Assertions.assertFalse(combinedUML.hasPlaceholders());
    }

    @Test
    void combine() {
        fail("write a test for this one!");
    }
}

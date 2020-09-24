package model;

import Integration.RoyTests;
import adaschema.CompilationUnit;
import exceptions.*;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.enums.PlaceholderPreferenceEnum;
import model.enums.VisibilityEnum;
import model.properties.ClassProperty;
import model.properties.EnumerationProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class UMLTest {

    @Test
    void test_fixGlobalPlaceholders() throws Gnat2XmlException, JAXBException, UnknownTypeException, NamingException, UnhandledTypeException, URISyntaxException, PartialUMLException {
        File adaFile = Paths.get(RoyTests.class.getClassLoader().getResource("SimpleInterFileAssociation/Class1.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML class1uml = Extractor.extractHighLevelConcepts(compilationUnit);

        assertFalse(class1uml.hasPlaceholders());

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

        assertFalse(combinedUML.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario1_veryBasic_Local_SamePackage() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Class c1 = new Class("C1");
        p1.addClass(c1);
        c1.addProperty(new ClassProperty("CP1", VisibilityEnum.Public,"C2"));

        assertTrue(p1.hasPlaceholders());
        assertTrue(c1.hasPlaceholders());
        assertTrue(uml.hasPlaceholders());

        Class c2 = new Class("C2");
        p1.addClass(c2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);
        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario2_veryBasic_Local_DifferentPackage() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Class c1 = new Class("C1");
        p1.addClass(c1);
        c1.addProperty(new ClassProperty("CP1", VisibilityEnum.Public,"C2"));

        assertTrue(p1.hasPlaceholders());
        assertTrue(c1.hasPlaceholders());
        assertTrue(uml.hasPlaceholders());

        Package p2 = new Package("P2");
        Class c2 = new Class("C2");
        p2.addClass(c2);
        uml.addPackage(p2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);
        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario3_veryBasic_Local_DifferentPackage_WithDots() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Class c1 = new Class("C1");
        p1.addClass(c1);
        c1.addProperty(new ClassProperty("CP1", VisibilityEnum.Public,"P2.C2"));

        assertTrue(p1.hasPlaceholders());
        assertTrue(c1.hasPlaceholders());
        assertTrue(uml.hasPlaceholders());

        Package p2 = new Package("P2");
        Class c2 = new Class("C2");
        p2.addClass(c2);
        uml.addPackage(p2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);
        assertTrue(uml.hasPlaceholders(),"Local should not fix this!");

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Global);
        assertFalse(uml.hasPlaceholders(),"Global should fix this!");
    }

    @Test
    void testPlaceholderFixScenario4_veryBasic_Local_All_Enums() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Enumeration e1 = new Enumeration("E1");
        e1.addProperty(new EnumerationProperty("EP1","E2"));
        p1.addEnumeration(e1);

        assertTrue(uml.hasPlaceholders());

        Enumeration e2 = new Enumeration("E2");
        p1.addEnumeration(e2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario4_veryBasic_Local_Enums_and_Classes_Mix() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Enumeration e1 = new Enumeration("E1");
        e1.addProperty(new EnumerationProperty("EP1","C2"));
        p1.addEnumeration(e1);

        assertTrue(uml.hasPlaceholders());

        Class c2 = new Class("C2");
        p1.addClass(c2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void combine() {
        fail("write a test for this one!");
    }
}

package model;

import Integration.RoyTests;
import adaschema.CompilationUnit;
import exceptions.*;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.enums.PlaceholderPreferenceEnum;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.parameters.ClassParameter;
import model.parameters.Parameter;
import model.parameters.PrimitiveParameter;
import model.properties.ClassProperty;
import model.properties.EnumerationProperty;
import model.properties.PrimitiveProperty;
import model.properties.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import template.TemplateInterpreter;
import template.TemplateParser;
import template.model.Template;
import utils.XMLUtils;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
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
    void testPlaceholderFixScenario4_veryBasic_Local_Enums_and_Classes_Mix_EnumPropWithClassPlaceholderInEnum() {
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
    void testPlaceholderFixScenario4_veryBasic_Local_Enums_and_Classes_Mix_EnumPropWithClassPlaceholderInClass() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Class e1 = new Class("E1");
        e1.addProperty(new EnumerationProperty("EP1","C2"));
        p1.addClass(e1);

        assertTrue(uml.hasPlaceholders());

        Class c2 = new Class("C2");
        p1.addClass(c2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario4_veryBasic_Local_Enums_and_Classes_Mix_ClassPropWithEnumPlaceholderInEnum() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Enumeration e1 = new Enumeration("E1");
        e1.addProperty(new ClassProperty("CP1",VisibilityEnum.Public,"E2"));
        p1.addEnumeration(e1);

        assertTrue(uml.hasPlaceholders());

        Enumeration e2 = new Enumeration("E2");
        p1.addEnumeration(e2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario4_veryBasic_Local_Enums_and_Classes_Mix_ClassPropWithEnumPlaceholderInClass() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Class e1 = new Class("E1");
        e1.addProperty(new ClassProperty("CP1",VisibilityEnum.Public,"E2"));
        p1.addClass(e1);

        assertTrue(uml.hasPlaceholders());

        Enumeration e2 = new Enumeration("E2");
        p1.addEnumeration(e2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario4_veryBasic_Local_Enums_and_Classes_Mix_ClassPropWithClassPlaceholderInClass() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Class e1 = new Class("E1");
        e1.addProperty(new ClassProperty("CP1",VisibilityEnum.Public,"E2"));
        p1.addClass(e1);

        assertTrue(uml.hasPlaceholders());

        Class e2 = new Class("E2");
        p1.addClass(e2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void testPlaceholderFixScenario4_veryBasic_Local_Enums_and_Classes_Mix_EnumPropWithEnumPlaceholderInEnum() {
        UML uml = new UML("UML1");
        Package p1 = new Package("P1");
        uml.addPackage(p1);
        Enumeration e1 = new Enumeration("E1");
        e1.addProperty(new EnumerationProperty("CP1","E2"));
        p1.addEnumeration(e1);

        assertTrue(uml.hasPlaceholders());

        Enumeration e2 = new Enumeration("E2");
        p1.addEnumeration(e2);

        uml.fixPlaceholders(PlaceholderPreferenceEnum.Local);

        assertFalse(uml.hasPlaceholders());
    }

    @Test
    void combine_verySimple() {
        UML uml1 = new UML("uml1");
        Package p1 = new Package("P1");
        uml1.addPackage(p1);
        Class c1 = new Class("C1");
        p1.addClass(c1);

        UML uml2 = new UML("uml2");
        Package p2 = new Package("P2");
        uml2.addPackage(p2);

        uml1.combine(uml2);
        assertEquals(2,uml1.getPackages().size());

        assertEquals(1,uml1.getPackages().get(0).getClasses().size());
    }

    @Test
    void combine_verySimple_sameNamePackage() {
        UML uml1 = new UML("uml1");
        Package p1 = new Package("P1");
        uml1.addPackage(p1);

        UML uml2 = new UML("uml2");
        Package p2 = new Package("P1");
        uml2.addPackage(p2);
        Class c2 = new Class("C2");
        p2.addClass(c2);

        uml1.combine(uml2);
        assertEquals(1,uml1.getPackages().size());

        assertEquals(1,uml1.getPackages().get(0).getClasses().size());
    }

    @Test
    void combine_verySimple_sameNamePackage_3umls() {
        UML uml1 = new UML("uml1");
        Package p1 = new Package("P1");
        uml1.addPackage(p1);

        UML uml2 = new UML("uml2");
        Package p2 = new Package("P1");
        uml2.addPackage(p2);
        Class c2 = new Class("C2");
        p2.addClass(c2);

        UML uml3 = new UML("uml3");
        Package p3 = new Package("P1");
        uml3.addPackage(p3);
        Class c3 = new Class("C3");
        p3.addClass(c3);

        uml1.combine(uml2);
        uml1.combine(uml3);
        assertEquals(1,uml1.getPackages().size());

        assertEquals(2,uml1.getPackages().get(0).getClasses().size());
    }

    @Test
    void combine_VariousElements() {
        UML uml1 = new UML("uml1");
        Package p1 = new Package("P1");
        uml1.addPackage(p1);

        UML uml2 = new UML("uml2");
        Package p2 = new Package("P1");
        uml2.addPackage(p2);
        Class c2 = new Class("C2");
        p2.addClass(c2);
        Enumeration e1 = new Enumeration("E1");
        p2.addEnumeration(e1);

        UML uml3 = new UML("uml3");
        Package p3 = new Package("P1");
        uml3.addPackage(p3);
        Class c3 = new Class("C3");
        p3.addClass(c3);
        CustomPrimitive cp1 = new CustomPrimitive("CP1", TypeEnum.Integer);
        p3.addCustomPrimitive(cp1);

        uml1.combine(uml2);
        uml1.combine(uml3);
        assertEquals(1,uml1.getPackages().size());

        assertEquals(2,uml1.getPackages().get(0).getClasses().size());
        assertEquals(1,uml1.getPackages().get(0).getEnumerations().size());
        assertEquals(1,uml1.getPackages().get(0).getCustomPrimitives().size());
    }

    @Test
    void replaceReferences() throws URISyntaxException, IOException {
        Template adaTemplate = TemplateParser.parseTemplateFromString(Files.readAllLines(Paths.get(UMLTest.class.getClassLoader().getResource("template/fixReferences.template").toURI())));
        String adaXML = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(UMLTest.class.getClassLoader().getResource("template/fixReferences.xml").toURI())));
        UML result = TemplateInterpreter.interpret(XMLUtils.convertStringToDocument(adaXML), adaTemplate);
        assertEquals(2, result.getClasses().size());
        assertEquals(1, result.getClasses().get(1).getOperations().size());
        assertEquals(3, result.getClasses().get(1).getOperations().get(0).getParameters().size());

        result.replaceReferences();

        assertEquals(PrimitiveParameter.class, result.getClasses().get(1).getOperations().get(0).getParameters().get(1).getClass());
        assertEquals(TypeEnum.Integer, ((PrimitiveParameter) result.getClasses().get(1).getOperations().get(0).getParameters().get(1)).getType());

        assertEquals(PrimitiveParameter.class, result.getClasses().get(1).getOperations().get(0).getParameters().get(2).getClass());
        assertEquals(TypeEnum.Real, ((PrimitiveParameter) result.getClasses().get(1).getOperations().get(0).getParameters().get(2)).getType());

        assertEquals(ClassParameter.class, result.getClasses().get(1).getOperations().get(0).getParameters().get(0).getClass());
        assertEquals("helloWorld", ((ClassParameter) result.getClasses().get(1).getOperations().get(0).getParameters().get(0)).getType().getName());
    }
}

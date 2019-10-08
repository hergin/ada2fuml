package Integration;

import adaschema.CompilationUnit;
import exceptions.*;
import exporter.Processor;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import gnat2xml.Gnat2XmlRunnerTests;
import model.UML;
import model.enums.TypeEnum;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class IntegrationTests {

    @Test
    public void Integrate_All() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(Gnat2XmlRunnerTests.class.getClassLoader().getResource("CombinedTypesAndVariables.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals("SomeClass",resultUml.getPackages().get(0).getName());

        Assertions.assertEquals(2,resultUml.getPackages().get(0).getClasses().size());

        Assertions.assertEquals(1,resultUml.getClasses().size());
        Assertions.assertEquals("SomeClass",resultUml.getClasses().get(0).getName());
    }

    @Test
    public void Integrate_All_2() throws URISyntaxException, Gnat2XmlException, UnhandledTypeException, NamingException, UnknownTypeException, JAXBException, PartialUMLException {
        File adaFile = Paths.get(Gnat2XmlRunnerTests.class.getClassLoader().getResource("SimpleInFileAssociation/ClassProperty.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        UML resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals("SomeClass",resultUml.getPackages().get(0).getName());

        Assertions.assertEquals(2,resultUml.getPackages().get(0).getClasses().size());

        Assertions.assertEquals("SomeClass1",resultUml.getPackages().get(0).getClasses().get(0).getName());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(0).getProperties().size());
        Assertions.assertEquals("someAttribute",resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(0).getName());
        Assertions.assertEquals(TypeEnum.Integer, ((PrimitiveProperty) resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(0)).getType());
        //Assertions.assertEquals(1, ((PrimitiveProperty) resultUml.getPackages().get(0).getClasses().get(0).getProperties().get(0)).getDefaultValue());


        Assertions.assertEquals("SomeClass2",resultUml.getPackages().get(0).getClasses().get(1).getName());
        Assertions.assertEquals(1,resultUml.getPackages().get(0).getClasses().get(1).getProperties().size());
        Assertions.assertEquals("someAttribute2",resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(0).getName());
        Assertions.assertEquals("SomeClass1", ((ClassProperty) resultUml.getPackages().get(0).getClasses().get(1).getProperties().get(0)).getType().getName());

    }

    @Test
    public void Integrate_All_Class2_File() throws Gnat2XmlException, UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException, URISyntaxException, NamingException, JAXBException {
        Processor.ID_COUNTER = 0;

        File adaFile = Paths.get(Gnat2XmlRunnerTests.class.getClassLoader().getResource("Class2.ads").toURI()).toFile();
        String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);

        UML resultUml = null;
        try {
            resultUml = Extractor.extractHighLevelConcepts(compilationUnit);
        } catch (PartialUMLException pmu) {
            resultUml = pmu.getPartialUML();
        }

        Assertions.assertEquals(1,resultUml.getPackages().size());

        String resultingXMI = Processor.processUML(resultUml);

        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"Class2\"><packagedElement xmi:type=\"uml:Package\" xmi:id=\"ID1\" name=\"Class2\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID2\" name=\"Class1Array\"></packagedElement></packagedElement><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID3\" name=\"Class2\"><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID4\" name=\"Class1List\" visibility=\"public\" type=\"ID2\"/><ownedOperation xmi:type=\"uml:Operation\" xmi:id=\"ID5\" name=\"Initialize\" visibility=\"public\"><ownedParameter xmi:type=\"uml:Parameter\" xmi:id=\"ID6\" name=\"Initialize_Return\" visibility=\"public\" type=\"ID3\" direction=\"return\"/><ownedParameter xmi:type=\"uml:Parameter\" xmi:id=\"ID7\" name=\"Max\" visibility=\"public\" direction=\"in\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#UnlimitedNatural\"/></ownedParameter></ownedOperation></packagedElement></uml:Model></xmi:XMI>",resultingXMI);
    }

}

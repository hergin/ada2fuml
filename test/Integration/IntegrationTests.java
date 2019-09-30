package Integration;

import exporter.Processor;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import gnat2xml.Gnat2XmlRunnerTests;
import model.Property;
import model.enums.TypeEnum;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class IntegrationTests {

    @Test
    public void Integrate_All() throws URISyntaxException {
        var adaFile = Paths.get(Gnat2XmlRunnerTests.class.getClassLoader().getResource("CombinedTypesAndVariables.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals("SomeClass",resultUml.getPackages().get(0).getName());

        Assertions.assertEquals(2,resultUml.getPackages().get(0).getClasses().size());

        Assertions.assertEquals(1,resultUml.getClasses().size());
        Assertions.assertEquals("SomeClass",resultUml.getClasses().get(0).getName());
    }

    @Test
    public void Integrate_All_2() throws URISyntaxException {
        var adaFile = Paths.get(Gnat2XmlRunnerTests.class.getClassLoader().getResource("SimpleInFileAssociation/ClassProperty.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        resultUml.replacePlaceholders();

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
    public void Integrate_All_Class2_File() throws Exception {
        var adaFile = Paths.get(Gnat2XmlRunnerTests.class.getClassLoader().getResource("Class2.ads").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        resultUml.replacePlaceholders();

        Assertions.assertEquals(1,resultUml.getPackages().size());

        var resultingXMI = Processor.processUML(resultUml);

        Assertions.assertEquals("",resultingXMI);
    }

}

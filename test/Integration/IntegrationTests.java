package Integration;

import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import gnat2xml.Gnat2XmlRunnerTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class IntegrationTests {

    @Test
    public void Integrate_All() throws URISyntaxException {
        var adaFile = Paths.get(Gnat2XmlRunnerTests.class.getClassLoader().getResource("CombinedTypesAndVariables.adb").toURI()).toFile();
        var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
        var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultUml.getPackages().size());
        Assertions.assertEquals("SomeClass",resultUml.getPackages().get(0).getName());

        Assertions.assertEquals(2,resultUml.getPackages().get(0).getClasses().size());

        Assertions.assertEquals(1,resultUml.getClasses().size());
        Assertions.assertEquals("SomeClass",resultUml.getClasses().get(0).getName());
    }

}

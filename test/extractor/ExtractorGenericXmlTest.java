package extractor;

import exceptions.InvalidSignatureException;
import model.UML;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import xmlparsing.GenericXmlParser;

import static extractor.ExtractorTests.GetAdaXMLFromResource;
import static org.junit.jupiter.api.Assertions.*;

class ExtractorGenericXmlTest {

    @Test
    void extractHighLevelConceptsBasicTest() throws InvalidSignatureException {
        String xmlString = GetAdaXMLFromResource("ExamplesFromRoy/xmi-files/globals_example1.ads.xml");
        Document xmlDocument = GenericXmlParser.getDocumentFromXML(xmlString);

        UML resultingUml = Extractor.extractHighLevelConcepts(xmlDocument);

        assertEquals(4, resultingUml.collectAllClasses().size());
    }

}
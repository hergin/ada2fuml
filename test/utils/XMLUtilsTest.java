package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static org.junit.jupiter.api.Assertions.*;

class XMLUtilsTest {

    String xmlString;

    @BeforeEach
    void setUp() {
        xmlString = "<bookstore>\n" +
                "<book category=\"cooking\">\n" +
                "<title lang=\"en\">Everyday Italian</title>\n" +
                "<author>Giada De Laurentiis</author>\n" +
                "<year>2005</year>\n" +
                "<price>30.00</price>\n" +
                "</book>\n" +
                "<book category=\"children\">\n" +
                "<title lang=\"en\">Harry Potter</title>\n" +
                "<author>J K. Rowling</author>\n" +
                "<year>2005</year>\n" +
                "<price>29.99</price>\n" +
                "</book>\n" +
                "<book category=\"web\">\n" +
                "<title lang=\"en\">XQuery Kick Start</title>\n" +
                "<author>James McGovern</author>\n" +
                "<author>Per Bothner</author>\n" +
                "<author>Kurt Cagle</author>\n" +
                "<author>James Linn</author>\n" +
                "<author>Vaidyanathan Nagarajan</author>\n" +
                "<year>2003</year>\n" +
                "<price>49.99</price>\n" +
                "</book>\n" +
                "<book category=\"web\" cover=\"paperback\">\n" +
                "<title lang=\"en\">Learning XML</title>\n" +
                "<author>Erik T. Ray</author>\n" +
                "<year>2003</year>\n" +
                "<price>39.95</price>\n" +
                "</book>\n" +
                "</bookstore>";
    }

    @Test
    void getAllNodesWithThePathTest() {
        Document xmlDocument = XMLUtils.convertStringToDocument(xmlString);
        assertEquals(1, XMLUtils.getAllNodesWithThePath(xmlDocument.getDocumentElement(), "/bookstore").getLength());
        assertEquals(4, XMLUtils.getAllNodesWithThePath(xmlDocument, "/bookstore/book").getLength());
        assertEquals(4, XMLUtils.getAllNodesWithThePath(XMLUtils.getAllNodesWithThePath(xmlDocument.getDocumentElement(), "/bookstore").item(0), "book").getLength());
    }

    @Test
    void convertStringToDocumentTest() {
        Document xmlDocument = XMLUtils.convertStringToDocument(xmlString);
        assertEquals(4, xmlDocument.getElementsByTagName("book").getLength());
    }
}
package template;

import Integration.RoyTests;
import model.UML;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import template.model.*;
import utils.XMLUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateInterpreterTest {

    Document bookXmlDocument;
    Template bookTemplate;

    @BeforeEach
    void setUp() {
        String xmlString = "<bookstore>\n" +
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
        bookXmlDocument = XMLUtils.convertStringToDocument(xmlString);

        bookTemplate = new Template();
        LHS lhs1 = new LHSTag("/bookstore");
        RHS rhs1 = new RHSClass("UML");
        TemplateItem bookstore = new TemplateItem(lhs1, rhs1);
        bookTemplate.addItem(bookstore);
        TemplateItem book = new TemplateItem(new LHSTag("book"), new RHSAttributeInClass("Class", "classes"));
        bookstore.addSubItem(book);
        TemplateItem category = new TemplateItem(new LHSAttribute("category"), new RHSAttribute("name"));
        book.addSubItem(category);
        TemplateItem title = new TemplateItem(new LHSTag("title"), new RHSAttributeInClass("PrimitiveProperty(String)", "properties"));
        book.addSubItem(title);
        TemplateItem value = new TemplateItem(new LHSAttribute("value"), new RHSAttribute("defaultValue"));
        title.addSubItem(value);
    }

    @Test
    void interpretTest() {
        UML result = TemplateInterpreter.interpret(bookXmlDocument, bookTemplate);
        assertEquals(4, result.getClasses().size());
    }

    @Test
    void basicAdaXMLTest() throws URISyntaxException, IOException {
        Template adaTemplate = TemplateParser.parseTemplateFromString(Files.readAllLines(Paths.get(TemplateInterpreterTest.class.getClassLoader().getResource("template/basicAdaTemplate.template").toURI())));
        String adaXML = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(TemplateInterpreterTest.class.getClassLoader().getResource("ExamplesFromRoy/xmi-files/globals_example1.ads.xml").toURI())));
        UML result = TemplateInterpreter.interpret(XMLUtils.convertStringToDocument(adaXML), adaTemplate);
        //assertEquals("Globals_Example1",result.getName());
        assertEquals(1, result.getPackages().size());
        assertEquals(4, result.getPackages().get(0).getClasses().size());
        assertEquals("Itype", result.getPackages().get(0).getClasses().get(0).getName());
        assertEquals("Record_With_Float_Rtype", result.getPackages().get(0).getClasses().get(3).getName());
    }
}
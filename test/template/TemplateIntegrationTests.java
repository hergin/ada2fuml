package template;

import model.UML;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import template.model.Template;
import utils.XMLUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateIntegrationTests {

    @Test
    void templateIntegrationTest1() {
        String bookTemplate = "/bookstore              --      UML\n" +
                "> book                  --     Class in classes\n" +
                ">> @category            --    name\n" +
                ">> title                --    Property in properties\n" +
                ">>> @value              --   value\n" +
                ">> author               --    Property in properties\n" +
                ">>> @value              --   value\n" +
                ">> year                 --    Property in properties\n" +
                ">>> @value              --   value\n" +
                ">> price                --    Property in properties\n" +
                ">>> @value              --   value";
        Template template = TemplateParser.parseTemplateFromString(bookTemplate);

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
        Document xmlDocument = XMLUtils.convertStringToDocument(xmlString);

        UML result = TemplateInterpreter.interpret(xmlDocument, template);
        assertEquals(4, result.getClasses().size());
    }

}

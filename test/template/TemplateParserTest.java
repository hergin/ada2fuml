package template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import template.model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TemplateParserTest {

    String bookTemplate = null;

    @BeforeEach
    void setUp() {
        bookTemplate = "/bookstore              --      UML\n" +
                "> book                  --     Class in classes\n" +
                ">> @category            --    name\n" +
                ">> title                --    PrimitiveProperty(String) in properties\n" +
                ">>> @value              --   defaultValue\n" +
                ">>> [String]              --   reference\n" +
                ">> author               --    PrimitiveProperty(String) in properties\n" +
                ">>> @value              --   defaultValue\n" +
                ">> year                 --    PrimitiveProperty(Integer) in properties\n" +
                ">>> @value              --   defaultValue\n" +
                ">> price                --    PrimitiveProperty(Real) in properties\n" +
                ">>> @value              --   defaultValue";
    }

    @Test
    void parseTemplateFromStringList() {
        List<String> list = Arrays.asList(bookTemplate.split("\n"));
        Template result = TemplateParser.parseTemplateFromString(list);
        assertEquals(1, result.getItems().size());
        assertEquals("PrimitiveProperty(String)", ((RHSAttributeInClass) result.getItems().get(0).getSubItems().get(0).getSubItems().get(1).getRhs()).getClassName());
        assertEquals(LHSLiteral.class, result.getItems().get(0).getSubItems().get(0).getSubItems().get(1).getSubItems().get(1).getLhs().getClass());
        assertEquals("String", ((LHSLiteral) result.getItems().get(0).getSubItems().get(0).getSubItems().get(1).getSubItems().get(1).getLhs()).getValue());
        assertEquals("PrimitiveProperty(Real)", ((RHSAttributeInClass) result.getItems().get(0).getSubItems().get(0).getSubItems().get(4).getRhs()).getClassName());
    }

    @Test
    void parseTemplateFromStringSingle() {
        Template result = TemplateParser.parseTemplateFromString(bookTemplate);
        assertEquals(1, result.getItems().size());
        assertEquals("PrimitiveProperty(Real)", ((RHSAttributeInClass) result.getItems().get(0).getSubItems().get(0).getSubItems().get(4).getRhs()).getClassName());
    }

    @Test
    void parseAdaTemplateWithAttributeInPath() throws URISyntaxException, IOException {
        Template adaTemplate = TemplateParser.parseTemplateFromString(Files.readAllLines(Paths.get(TemplateParserTest.class.getClassLoader().getResource("template/basicAdaTemplate.template").toURI())));
        assertEquals(((LHSAttributeWithPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(0).getLhs()).getPath(), "names_ql/defining_identifier");
        assertEquals(((LHSAttributeWithPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(0).getLhs()).getAttributeName(), "def_name");
        assertEquals(adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(0).getLhs().toString(), "names_ql/defining_identifier/@def_name");
    }

    @Test
    void parseAncestorPath() throws URISyntaxException, IOException {
        Template adaTemplate = TemplateParser.parseTemplateFromString(Files.readAllLines(Paths.get(TemplateParserTest.class.getClassLoader().getResource("template/getTagFromSameLevel.template").toURI())));
        assertEquals(1, ((LHSAncestorPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(2).getLhs()).getLevel());
        assertEquals("operation", ((LHSAncestorPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(2).getLhs()).getTag());
        assertEquals("../operation", ((LHSAncestorPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(2).getLhs()).toString());
        assertEquals(1, ((LHSAncestorPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(2).getLhs()).getLevel());
        assertEquals("operation", ((LHSAncestorPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(2).getLhs()).getTag());
        assertEquals(1, adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(2).getAlternateLhs().size());
        assertEquals("./operation", ((LHSAncestorPath) adaTemplate.getItems().get(0).getSubItems().get(1).getSubItems().get(2).getAlternateLhs().get(0)).toString());
    }
}
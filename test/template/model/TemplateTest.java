package template.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {

    @Test
    void basicTemplateTest_book() {
        Template template = new Template();
        LHS lhs1 = new LHSTag("/bookstore");
        RHS rhs1 = new RHSClass("UML");
        TemplateItem bookstore = new TemplateItem(lhs1, rhs1);
        template.addItem(bookstore);
        TemplateItem book = new TemplateItem(new LHSTag("book"), new RHSAttributeInClass("Class", "classes"));
        bookstore.addSubItem(book);
        TemplateItem category = new TemplateItem(new LHSAttribute("category"), new RHSAttribute("name"));
        book.addSubItem(category);
        TemplateItem title = new TemplateItem(new LHSTag("title"), new RHSAttributeInClass("PrimitiveProperty(String)", "properties"));
        book.addSubItem(title);
        TemplateItem value = new TemplateItem(new LHSAttribute("value"), new RHSAttribute("defaultValue"));
        title.addSubItem(value);
        assertEquals("/bookstore -- UML" + System.lineSeparator() +
                ">book -- Class in classes" + System.lineSeparator() +
                ">>@category -- name" + System.lineSeparator() +
                ">>title -- PrimitiveProperty(String) in properties" + System.lineSeparator() +
                ">>>@value -- defaultValue", template.toString());
    }

    @Test
    void basicTemplateTest_Ada() {
        Template template = new Template();
        LHS lhs1 = new LHSTag("/compilation_unit");
        RHS rhs1 = new RHSClass("UML");
        TemplateItem compilationUnit = new TemplateItem(lhs1, rhs1);
        template.addItem(compilationUnit);
        TemplateItem name = new TemplateItem(new LHSAttribute("def_name"), new RHSAttribute("name"));
        compilationUnit.addSubItem(name);

        TemplateItem packageDecl = new TemplateItem(new LHSTag("unit_declaration_q/package_declaration"), new RHSAttributeInClass("Package", "packages"));
        compilationUnit.addSubItem(packageDecl);
        TemplateItem packageDeclName = new TemplateItem(new LHSAttributeWithPath("def_name", "names_ql/defining_identifier"), new RHSAttribute("name"));
        packageDecl.addSubItem(packageDeclName);

        TemplateItem ordinaryTypeDecl = new TemplateItem(new LHSTag("visible_part_declarative_items_ql/ordinary_type_declaration"), new RHSAttributeInClass("Class", "classes"));
        packageDecl.addSubItem(ordinaryTypeDecl);
        TemplateItem ordinaryTypeDeclName = new TemplateItem(new LHSAttributeWithPath("def_name", "names_ql/defining_identifier"), new RHSAttribute("name"));
        ordinaryTypeDecl.addSubItem(ordinaryTypeDeclName);

        TemplateItem subTypeDecl = new TemplateItem(new LHSTag("visible_part_declarative_items_ql/subtype_declaration"), new RHSAttributeInClass("Primitive", "primitives"));
        packageDecl.addSubItem(subTypeDecl);
        TemplateItem subTypeDeclName = new TemplateItem(new LHSAttributeWithPath("def_name", "names_ql/defining_identifier"), new RHSAttribute("name"));
        subTypeDecl.addSubItem(subTypeDeclName);

        TemplateItem subTypeMark = new TemplateItem(new LHSTag("type_declaration_view_q/subtype_indication/subtype_mark_q"), new RHSAttribute("superPrimitive"));
        subTypeDecl.addSubItem(subTypeMark);
        TemplateItem subTypeMarkName = new TemplateItem(new LHSAttributeWithPath("ref_name", "identifier"), new RHSAttribute("name"));
        subTypeMark.addSubItem(subTypeMarkName);

        assertEquals("/compilation_unit -- UML" + System.lineSeparator() +
                ">@def_name -- name" + System.lineSeparator() +
                ">unit_declaration_q/package_declaration -- Package in packages" + System.lineSeparator() +
                ">>names_ql/defining_identifier/@def_name -- name" + System.lineSeparator() +
                ">>visible_part_declarative_items_ql/ordinary_type_declaration -- Class in classes" + System.lineSeparator() +
                ">>>names_ql/defining_identifier/@def_name -- name" + System.lineSeparator() +
                ">>visible_part_declarative_items_ql/subtype_declaration -- Primitive in primitives" + System.lineSeparator() +
                ">>>names_ql/defining_identifier/@def_name -- name" + System.lineSeparator() +
                ">>>type_declaration_view_q/subtype_indication/subtype_mark_q -- superPrimitive" + System.lineSeparator() +
                ">>>>identifier/@ref_name -- name", template.toString());
    }

}
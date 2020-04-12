package processor;

import exceptions.StillHavePlaceHolderException;
import exceptions.UnknownParameterException;
import exceptions.UnknownPropertyException;
import exporter.Processor;
import exporter.StillHavePlaceholderExceptionPolicy;
import model.*;
import model.Class;
import model.Package;
import model.enums.DirectionEnum;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.parameters.ClassParameter;
import model.parameters.PrimitiveParameter;
import model.properties.AssociationProperty;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import utils.StringUtils;

import java.lang.reflect.Type;

class ProcessorTest {

    @BeforeEach
    void init() {
        Processor.ID_COUNTER = 0;
    }

    @Test
    void processEmptyPackages() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {
        UML uml = new UML("uml1");

        Package package1 = new Package("package1");
        Package package2 = new Package("package2");
        Package subPackage = new Package("subPackage");

        package1.addSubPackage(subPackage);
        uml.addPackage(package1);
        uml.addPackage(package2);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"uml1\"><packagedElement xmi:type=\"uml:Package\" xmi:id=\"ID1\" name=\"package1\"><packagedElement xmi:type=\"uml:Package\" xmi:id=\"ID3\" name=\"subPackage\"></packagedElement></packagedElement><packagedElement xmi:type=\"uml:Package\" xmi:id=\"ID2\" name=\"package2\"></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }

    @Test
    void processNestedClass() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {

        UML uml = new UML("uml");

        Class Class1 = new Class("Class1");
        Class Class2 = new Class("Class2");
        Class NestedClass = new Class("NestedClass");

        Class1.addNestedClass(NestedClass);

        uml.addClass(Class1);
        uml.addClass(Class2);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"uml\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID1\" name=\"Class1\"><nestedClassifier xmi:type=\"uml:Class\" xmi:id=\"ID3\" name=\"NestedClass\"></nestedClassifier></packagedElement><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID2\" name=\"Class2\"></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }

    @Test
    void TestProcessEmptyClass() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {

        UML uml = new UML("uml");
        Class SomeClass = new Class("SomeClass");

        uml.addClass(SomeClass);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"uml\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID1\" name=\"SomeClass\"></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }

    @Test
    void TestProcessPrimitiveStringProperty() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {
        UML uml = new UML("uml");
        Class SomeClass = new Class("SomeClass");
        PrimitiveProperty somePP = new PrimitiveProperty("someAttribute", VisibilityEnum.Private, TypeEnum.String,null);

        SomeClass.addProperty(somePP);
        uml.addClass(SomeClass);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"uml\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID1\" name=\"SomeClass\"><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID2\" name=\"someAttribute\" visibility=\"private\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#String\"/></ownedAttribute></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }

    @Test
    void processParameters() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {

        UML uml = new UML("uml");
        Operation operation = new Operation("operation1", VisibilityEnum.Public);
        Class Class1 = new Class("Class1");

        ClassParameter classParameter = new ClassParameter("classParameter", DirectionEnum.In, Class1);
        PrimitiveParameter primitiveParameter = new PrimitiveParameter("primitiveParameter", DirectionEnum.In, TypeEnum.Boolean);
        PrimitiveParameter primitiveParameterWithValue = new PrimitiveParameter("primitiveParameter", DirectionEnum.In, TypeEnum.Integer, 99);

        Class1.addOperation(operation);

        operation.addParameter(classParameter);
        operation.addParameter(primitiveParameter);
        operation.addParameter(primitiveParameterWithValue);

        uml.addClass(Class1);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"uml\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID2\" name=\"Class1\"><ownedOperation xmi:type=\"uml:Operation\" xmi:id=\"ID1\" name=\"operation1\" visibility=\"public\"><ownedParameter xmi:type=\"uml:Parameter\" xmi:id=\"ID3\" name=\"classParameter\" visibility=\"public\" type=\"ID2\" direction=\"in\"/><ownedParameter xmi:type=\"uml:Parameter\" xmi:id=\"ID4\" name=\"primitiveParameter\" visibility=\"public\" direction=\"in\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#Boolean\"/></ownedParameter><ownedParameter xmi:type=\"uml:Parameter\" xmi:id=\"ID5\" name=\"primitiveParameter\" visibility=\"public\" direction=\"in\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#Integer\"/><defaultValue xmi:type=\"uml:LiteralInteger\" xmi:id=\"ID6\" value=\"99\"/></ownedParameter></ownedOperation></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }

    @Test
    void TestClassPropertyAssociation() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {

        UML uml = new UML("c1c2assoc");

        Class c1 = new Class("C1");
        uml.addClass(c1);
        Class c2 = new Class("C2");
        uml.addClass(c2);

        PrimitiveProperty pp = new PrimitiveProperty("somePrimitiveProperty", VisibilityEnum.Public, TypeEnum.Integer,5);
        c1.addProperty(pp);

        ClassProperty cp = new ClassProperty("someClassProperty",VisibilityEnum.Public,c2);
        c1.addProperty(cp);

        Association assocBetweenC1andC2 = new Association("someAssociation",c1,c2);
        uml.addAssociation(assocBetweenC1andC2);
        AssociationProperty ap = new AssociationProperty("someAssociation",VisibilityEnum.Public,assocBetweenC1andC2);
        c1.addProperty(ap);

        AssociationProperty ap2 = new AssociationProperty("someAssociation",VisibilityEnum.Public,assocBetweenC1andC2);
        c2.addProperty(ap2);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"c1c2assoc\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID1\" name=\"C1\"><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID3\" name=\"somePrimitiveProperty\" visibility=\"public\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#Integer\"/><defaultValue xmi:type=\"uml:LiteralInteger\" xmi:id=\"ID8\" value=\"5\"/></ownedAttribute><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID4\" name=\"someClassProperty\" visibility=\"public\" type=\"ID2\"/><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID6\" name=\"someAssociation\" visibility=\"public\" type=\"ID2\" association=\"ID5\"/></packagedElement><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID2\" name=\"C2\"><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID7\" name=\"someAssociation\" visibility=\"public\" type=\"ID1\" association=\"ID5\"/></packagedElement><packagedElement xmi:type=\"uml:Association\" xmi:id=\"ID5\"><memberEnd xmi:idref=\"ID6\"/><memberEnd xmi:idref=\"ID7\"/></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }

    @Test
    void TestProcessSuperClass() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {
        UML uml = new UML("Model");
        Class Animal = new Class("Animal");
        Class Cat = new Class("Cat");

        PrimitiveProperty pp = new PrimitiveProperty("sound", VisibilityEnum.Public, TypeEnum.String, "miaovv");
        PrimitiveProperty pp2 = new PrimitiveProperty("name", VisibilityEnum.Private, TypeEnum.String, null);

        Animal.addProperty(pp2);
        Cat.addProperty(pp);

        Cat.addSuperClass(Animal);

        uml.addClass(Animal);
        uml.addClass(Cat);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"Model\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID1\" name=\"Animal\"><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID4\" name=\"name\" visibility=\"private\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#String\"/></ownedAttribute></packagedElement><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID2\" name=\"Cat\"><generalization xmi:type=\"uml:Generalization\" xmi:id=\"ID5\" general=\"ID1\"/><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID3\" name=\"sound\" visibility=\"public\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#String\"/><defaultValue xmi:type=\"uml:LiteralString\" xmi:id=\"ID6\" value=\"miaovv\"/></ownedAttribute></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }

    @Test
    void TestComprehensiveExample() throws UnknownParameterException, StillHavePlaceHolderException, UnknownPropertyException {
        UML uml = new UML("uml");
        Package package1 = new Package("package1");
        Class Class1 = new Class("Class1");
        Operation someOperation = new Operation("someOperation", VisibilityEnum.Private);
        PrimitiveProperty pp = new PrimitiveProperty("someProperty", VisibilityEnum.Private, TypeEnum.String, "hellomate");
        PrimitiveParameter unnamed1 = new PrimitiveParameter("unnamed1", DirectionEnum.InOut, TypeEnum.Integer);
        PrimitiveParameter returnValue = new PrimitiveParameter("return", DirectionEnum.Return, TypeEnum.Boolean);

        uml.addPackage(package1);
        package1.addClass(Class1);
        Class1.addOperation(someOperation);
        Class1.addProperty(pp);

        someOperation.addParameter(unnamed1);
        someOperation.addParameter(returnValue);

        String result = Processor.processUML(uml, StillHavePlaceholderExceptionPolicy.Throw);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:uml=\"http://www.omg.org/spec/UML/20131001\" xmlns:StandardProfile=\"http://www.omg.org/spec/UML/20131001/StandardProfile\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\"><uml:Model xmi:type=\"uml:Model\" xmi:id=\"ID0\" name=\"uml\"><packagedElement xmi:type=\"uml:Package\" xmi:id=\"ID1\" name=\"package1\"><packagedElement xmi:type=\"uml:Class\" xmi:id=\"ID2\" name=\"Class1\"><ownedAttribute xmi:type=\"uml:Property\" xmi:id=\"ID4\" name=\"someProperty\" visibility=\"private\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#String\"/><defaultValue xmi:type=\"uml:LiteralString\" xmi:id=\"ID7\" value=\"hellomate\"/></ownedAttribute><ownedOperation xmi:type=\"uml:Operation\" xmi:id=\"ID3\" name=\"someOperation\" visibility=\"private\"><ownedParameter xmi:type=\"uml:Parameter\" xmi:id=\"ID5\" name=\"unnamed1\" visibility=\"public\" direction=\"inout\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#Integer\"/></ownedParameter><ownedParameter xmi:type=\"uml:Parameter\" xmi:id=\"ID6\" name=\"return\" visibility=\"public\" direction=\"return\"><type href=\"http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#Boolean\"/></ownedParameter></ownedOperation></packagedElement></packagedElement></uml:Model></xmi:XMI>";

        XmlAssert.assertThat(expected)
                .and(StringUtils.sanitize(result))
                .ignoreWhitespace()
                .areIdentical();

        //Assertions.assertEquals(expected,result);
    }


}
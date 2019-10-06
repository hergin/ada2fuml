package extractor;

import exceptions.NamingException;
import exceptions.UnhandledTypeException;
import exceptions.UnknownTypeException;
import model.Operation;
import model.Parameter;
import model.Property;
import model.enums.TypeEnum;
import model.parameters.ClassParameter;
import model.parameters.PrimitiveParameter;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xmlparsing.AdaXmlParser;

import java.io.IOException;

class ExtractorTests {

    public static String GetAdaXMLFromResource(String name) {
        try {
            return new String(ExtractorTests.class.getClassLoader().getResource(name).openStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void packageX_and_typeX_yields_classX() throws UnhandledTypeException, NamingException, UnknownTypeException {
        var adaXML = GetAdaXMLFromResource("SingleClassWithSameName.ads.xml");

        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXML);

        var resultingUML = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultingUML.getClasses().size());
        Assertions.assertEquals("SomeClass",resultingUML.getClasses().get(0).getName());
    }

    @Test
    void packageX_and_typeY_yields_packageX_and_classY() throws UnhandledTypeException, NamingException, UnknownTypeException {
        var adaXML = GetAdaXMLFromResource("CombinedTypesAndVariables.ads.xml");

        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXML);

        var resultingUML = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultingUML.getPackages().size());
        Assertions.assertEquals("SomeClass",resultingUML.getPackages().get(0).getName());

        Assertions.assertEquals(2,resultingUML.getPackages().get(0).getClasses().size());
    }

    @Test
    void primitivePropertyTests() throws UnhandledTypeException, NamingException, UnknownTypeException {
        var adaXML = GetAdaXMLFromResource("CombinedTypesAndVariables.ads.xml");

        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXML);

        var resultingUML = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultingUML.getPackages().size());
        Assertions.assertEquals("SomeClass",resultingUML.getPackages().get(0).getName());

        Assertions.assertEquals(2,resultingUML.getPackages().get(0).getClasses().size());

        Assertions.assertEquals(2,resultingUML.getClasses().get(0).getProperties().size());
        Assertions.assertEquals("someAttribute",resultingUML.getClasses().get(0).getProperties().get(0).getName());
        Assertions.assertEquals(TypeEnum.Integer, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(0)).getType());

        Assertions.assertEquals(1,resultingUML.getPackages().get(0).getClasses().get(0).getProperties().size());
        Assertions.assertEquals(1,resultingUML.getPackages().get(0).getClasses().get(1).getProperties().size());

        Assertions.assertEquals("someAttribute3",resultingUML.getPackages().get(0).getClasses().get(1).getProperties().get(0).getName());
        Assertions.assertEquals(TypeEnum.Integer, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(1).getProperties().get(0)).getType());
    }

    @Test
    void variableAssignmentTests() throws UnhandledTypeException, NamingException, UnknownTypeException {
        var adaXML = GetAdaXMLFromResource("CombinedTypesAndVariables.ads.xml");

        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXML);

        var resultingUML = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(2,resultingUML.getClassByName("SomeClass").getProperties().size());
    }

    @Test
    void differentPropertyTypesDefaultValuesTests() throws UnhandledTypeException, NamingException, UnknownTypeException {
        var adaXML = GetAdaXMLFromResource("PrimitivePropertyTypes.ads.xml");
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXML);
        var resultingUML = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultingUML.getPackages().size());
        Assertions.assertEquals(1,resultingUML.getPackages().get(0).getClasses().size());

        Assertions.assertEquals(5,resultingUML.getPackages().get(0).getClasses().get(0).getProperties().size());

        Assertions.assertEquals(TypeEnum.Integer, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(0)).getType());
        Assertions.assertEquals(1, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(0)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.UnlimitedNatural, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(1)).getType());
        Assertions.assertEquals(2, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(1)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.Boolean, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(2)).getType());
        Assertions.assertEquals(false, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(2)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.String, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(3)).getType());
        Assertions.assertEquals("\"hello\"", ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(3)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.Real, ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(4)).getType());
        Assertions.assertEquals(1.1, ((Double) ((PrimitiveProperty) resultingUML.getPackages().get(0).getClasses().get(0).getProperties().get(4)).getDefaultValue()),0.000001);
    }

    @Test
    void differentVariableTypesDefaultValuesTests() throws UnhandledTypeException, NamingException, UnknownTypeException {
        var adaXML = GetAdaXMLFromResource("PrimitiveVariableTypes.ads.xml");
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXML);
        var resultingUML = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultingUML.getClasses().size());

        Assertions.assertEquals(7,resultingUML.getClasses().get(0).getProperties().size());

        Assertions.assertEquals(TypeEnum.Integer, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(0)).getType());
        Assertions.assertEquals(1, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(0)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.UnlimitedNatural, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(1)).getType());
        Assertions.assertEquals(2, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(1)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.Boolean, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(2)).getType());
        Assertions.assertEquals(false, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(2)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.String, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(3)).getType());
        Assertions.assertEquals("\"hello\"", ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(3)).getDefaultValue());

        Assertions.assertEquals(TypeEnum.Real, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(4)).getType());
        Assertions.assertEquals(1.1, ((Double) ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(4)).getDefaultValue()),0.000001);

        Assertions.assertEquals(TypeEnum.Real, ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(5)).getType());
        Assertions.assertEquals(null, ((Double) ((PrimitiveProperty) resultingUML.getClasses().get(0).getProperties().get(5)).getDefaultValue()));

        Assertions.assertEquals("Class1.Class1", ((ClassProperty) resultingUML.getClasses().get(0).getProperties().get(6)).getPlaceholder());

        Assertions.assertEquals(1, resultingUML.getClasses().get(0).getOperations().size());
        Assertions.assertEquals(2, resultingUML.getClasses().get(0).getOperations().get(0).getParameters().size());
        Assertions.assertEquals("Class1.Class1", ((ClassParameter) resultingUML.getClasses().get(0).getOperations().get(0).getParameters().get(0)).getPlaceholder());
    }

    @Test
    void parameterDefaultValueTesting() throws UnhandledTypeException, NamingException, UnknownTypeException {
        var adaXML = GetAdaXMLFromResource("ParameterDefaultValue.ads.xml");
        var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXML);
        var resultingUML = Extractor.extractHighLevelConcepts(compilationUnit);

        Assertions.assertEquals(1,resultingUML.getClasses().size());

        Assertions.assertEquals(2,resultingUML.getClasses().get(0).getOperations().size());

        Assertions.assertEquals(2,resultingUML.getClasses().get(0).getOperations().get(0).getParameters().size()); // One real param, one return param

        Assertions.assertEquals(2, ((PrimitiveParameter) resultingUML.getClasses().get(0).getOperations().get(0).getParameters().get(1)).getDefaultValue());

        Assertions.assertEquals(null, ((PrimitiveParameter) resultingUML.getClasses().get(0).getOperations().get(1).getParameters().get(1)).getDefaultValue());
    }
}

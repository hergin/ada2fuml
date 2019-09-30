package model;

import model.enums.TypeEnum;
import model.enums.VisibilityEnum;
import model.properties.ClassProperty;
import model.properties.PrimitiveProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest {

    @Test
    void ShouldHavePlaceholdersTrue_Property() {

        var sampleClass = new Class("Sample");

        var samplePlaceholderProperty = new ClassProperty("SampleProprty", VisibilityEnum.Public,"SomePlaceholder");

        sampleClass.addProperty(samplePlaceholderProperty);

        Assertions.assertTrue(sampleClass.hasPlaceholders());
    }

    @Test
    void ShouldHavePlaceholdersFalse_ClassProperty() {

        var sampleClass = new Class("Sample");

        var samplePlaceholderProperty = new ClassProperty("SampleProprty", VisibilityEnum.Public,sampleClass);

        sampleClass.addProperty(samplePlaceholderProperty);

        Assertions.assertFalse(sampleClass.hasPlaceholders());
    }

    @Test
    void ShouldHavePlaceholdersFalse_PrimitiveProperty() {

        var sampleClass = new Class("Sample");

        var samplePlaceholderProperty = new PrimitiveProperty("sampleProp",VisibilityEnum.Public, TypeEnum.Integer,5);

        sampleClass.addProperty(samplePlaceholderProperty);

        Assertions.assertFalse(sampleClass.hasPlaceholders());
    }
}
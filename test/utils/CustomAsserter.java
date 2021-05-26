package utils;

import model.UML;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomAsserter {

    public static void assertUML(UML expected, UML actual) {
        assertEquals(expected.getName(), actual.getName());
    }

}

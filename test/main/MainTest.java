package main;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void listFiles() {
        var result = Main.listAdaSourceFiles(".");
    }
}
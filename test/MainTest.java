import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class MainTest {

    @Test
    void listFiles() {
        List<File> result = Main.listAdaSourceFiles(".","ads");
    }
}
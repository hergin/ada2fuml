package exporter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class FileWriter {

    static void writeFile(StringBuilder inputString, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert out != null;
            out.write(inputString.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

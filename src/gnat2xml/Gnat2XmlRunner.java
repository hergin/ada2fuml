package gnat2xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Gnat2XmlRunner {

    public static String ConvertAdaCodeToXml(String resourceName) {
        StringBuilder result = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec("cmd /c gnat2xml "+resourceName);

            //p.waitFor();
            BufferedReader reader=new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

}

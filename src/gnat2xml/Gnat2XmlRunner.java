package gnat2xml;

import exceptions.Gnat2XmlException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Gnat2XmlRunner {

    public static String ConvertAdaCodeToXml(File adaFile) throws Gnat2XmlException {
        StringBuilder result = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec("cmd /c gnat2xml "+adaFile.getAbsolutePath());

            //p.waitFor();
            BufferedReader reader=new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new Gnat2XmlException("An exception occured while trying to execute gnat2xml command: "+e.getMessage());
        }

        return result.toString();
    }

}

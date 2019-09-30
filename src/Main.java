import exporter.Processor;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.UML;
import xmlparsing.AdaXmlParser;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var files = listAdaSourceFiles(".");

//        var overallUml = new UML(Paths.get(new File(".").toURI()).getParent().getFileName().toString());

        System.out.println("Found "+files.size()+" ada source files in the current directory!\n");

        for (var file : files) {

            try {
                System.out.println("Started processing: " + file.getName() + " file");
                var adaFile = file;

                System.out.print("Converting to XML...");
                var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
                System.out.println(" OK");

                System.out.print("Parsing compilation unit from XML...");
                var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
                System.out.println(" OK");

                System.out.print("Extracting UML concepts...");
                var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);
                System.out.println(" OK");

                if(resultUml.hasPlaceholders()) {
                    // TODO
                } else {
                    System.out.print("Exporting overall UML to XMI...");
                    var resultingXMI = Processor.processUML(resultUml);
                    System.out.println(" OK");

                    System.out.print("Writing to file: " + resultUml.getFileName() + ".xmi");
                    Files.write(Paths.get(resultUml.getFileName() + ".xmi"), resultingXMI.getBytes());
                    System.out.println(" OK");

                    System.out.println("File: " + resultUml.getFileName() + ".xmi is successfully created!\n");
                }
            } catch (Exception e) {
                System.out.println("\nEXCEPTION THROWN, SKIPPING TO NEXT FILE. Below is the message:\n"+e.getMessage()+"\n");
            }

        }
    }

    public static List<File> listAdaSourceFiles(String path) {
        File currentFolder = new File(path);
        return Arrays.asList(currentFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                //if(name.endsWith(".adb") || name.endsWith(".ads"))
                if(name.endsWith(".ads"))
                    return true;
                return false;
            }
        }));
    }

}

import exporter.Processor;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.UML;
import xmlparsing.AdaXmlParser;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        var files = listAdaSourceFiles(".");
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
                    System.out.println("CAN'T EXPORT: THIS UML HAS EXTERNAL PLACEHOLDERS THAT NEEDS TO BE RESOLVED!\n");
                } else {
                    System.out.print("Exporting overall UML to XMI...");
                    var resultingXMI = Processor.processUML(resultUml);
                    System.out.println(" OK");

                    System.out.print("Writing to file: " + resultUml.getFileName() + ".xmi");
                    if(!Files.exists(Paths.get("xmi-files"))) {
                        System.out.print("\nCreating xmi-files directory for the first time!");
                        Files.createDirectory(Paths.get("xmi-files"));
                    }
                    Files.write(Paths.get("xmi-files\\"+resultUml.getFileName() + ".xmi"), resultingXMI.getBytes());
                    System.out.println(" OK");

                    System.out.println("File: " + resultUml.getFileName() + ".xmi is successfully created!\n");
                }
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                System.out.println("\nEXCEPTION THROWN, SKIPPING TO NEXT FILE. Below is the message and the stacktrace:\n"+e.getMessage()+"\n"+sw.toString()+"\n");
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

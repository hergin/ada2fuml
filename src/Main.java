import exceptions.PartialUMLException;
import exceptions.StillHavePlaceHolderException;
import exceptions.UnknownParameterException;
import exceptions.UnknownPropertyException;
import exporter.Processor;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.UML;
import model.enums.PlaceholderPreferenceEnum;
import xmlparsing.AdaXmlParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        var files = listAdaSourceFiles(".");
        System.out.println("Found "+files.size()+" ada source files in the current directory!\n");

        var overallUML = new UML("Complete UML");

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
                UML resultUml = null;
                try {
                    resultUml = Extractor.extractHighLevelConcepts(compilationUnit);
                } catch (PartialUMLException pue) {
                    resultUml = pue.getPartialUML();
                    System.out.println("WARNING: SOME EXCEPTIONS ARE THROWN WHILE PRODUCING THIS UML! Below are the exceptions and the messages:\n");
                    for(var exception:pue.getCauses()) {
                        System.out.println(exception.getMessage());
                        exception.printStackTrace();
                    }
                }
                System.out.println(" OK");

                overallUML.combine(resultUml);

                if(resultUml.hasPlaceholders()) {
                    // TODO
                    System.out.println("WARNING: THIS UML HAS EXTERNAL PLACEHOLDERS THAT NEEDS TO BE RESOLVED! They will be resolved when all UML diagrams are combined!\n");
                } else {
                    System.out.print("Exporting resulting UML to XMI...");
                    var resultingXMI = Processor.processUML(resultUml);
                    System.out.println(" OK\n");

                    //System.out.print("Writing to file: " + resultUml.getFileName() + ".xmi");
                    //Files.write(Paths.get("xmi-files\\"+resultUml.getFileName() + ".xmi"), resultingXMI.getBytes());
                    //System.out.println(" OK");

                    //System.out.println("File: " + resultUml.getFileName() + ".xmi is successfully created!\n");
                }
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                System.out.println("\nEXCEPTION THROWN, SKIPPING TO NEXT FILE. Below is the message and the stacktrace:\n"+e.getMessage()+"\n"+sw.toString()+"\n");
            }
        }

        try{
            overallUML.fixPlaceholders(PlaceholderPreferenceEnum.Global);

            System.out.print("Exporting overall UML to XMI...");
            var resultingXMI = Processor.processUML(overallUML);
            System.out.println(" OK");

            System.out.print("Writing to file: Overall.xmi");
            if(!Files.exists(Paths.get("xmi-files"))) {
                System.out.print("\nCreating xmi-files directory!");
                Files.createDirectory(Paths.get("xmi-files"));
            }
            Files.write(Paths.get("xmi-files\\Overall.xmi"), resultingXMI.getBytes());
            System.out.println(" OK");

            System.out.println("File: Overall.xmi is successfully created!\n");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println("\nEXCEPTION THROWN, SKIPPING TO NEXT FILE. Below is the message and the stacktrace:\n"+e.getMessage()+"\n"+sw.toString()+"\n");
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

import adaschema.CompilationUnit;
import exceptions.PartialUMLException;
import exceptions.StillHavePlaceHolderException;
import exceptions.UnknownParameterException;
import exceptions.UnknownPropertyException;
import exporter.Processor;
import extractor.Extractor;
import gnat2xml.Gnat2XmlRunner;
import model.HierarchicalElement;
import model.UML;
import model.enums.PlaceholderPreferenceEnum;
import model.parameters.ClassParameter;
import model.properties.ClassProperty;
import xmlparsing.AdaXmlParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<File> files = listAdaSourceFiles(".");
        System.out.println("Found "+files.size()+" ada source files in the current directory!\n");

        UML overallUML = new UML("Complete UML");

        for (File file : files) {

            try {
                System.out.println("Started processing: " + file.getName() + " file");
                File adaFile = file;

                System.out.print("Converting to XML...");
                String adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
                System.out.println(" OK");

                System.out.print("Parsing compilation unit from XML...");
                CompilationUnit compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
                System.out.println(" OK");

                System.out.print("Extracting UML concepts...");
                UML resultUml = null;
                try {
                    resultUml = Extractor.extractHighLevelConcepts(compilationUnit);
                } catch (PartialUMLException pue) {
                    resultUml = pue.getPartialUML();
                    System.out.println("WARNING: SOME EXCEPTIONS ARE THROWN WHILE PRODUCING THIS UML! Below are the exceptions and the messages:\n");
                    for(Exception exception:pue.getCauses()) {
                        System.out.println(exception.getMessage());
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        exception.printStackTrace(pw);
                        System.out.println(sw.toString());
                    }
                }
                System.out.println(" OK");

                overallUML.combine(resultUml);

                System.out.print("Exporting resulting UML to XMI...");
                String resultingXMI = Processor.processUML(resultUml);
                System.out.println(" OK\n");

                //System.out.print("Writing to file: " + resultUml.getFileName() + ".xmi");
                //Files.write(Paths.get("xmi-files\\"+resultUml.getFileName() + ".xmi"), resultingXMI.getBytes());
                //System.out.println(" OK");

                //System.out.println("File: " + resultUml.getFileName() + ".xmi is successfully created!\n");

            } catch (StillHavePlaceHolderException shphe) {
                System.out.println("\nWARNING: "+shphe.getMessage()+" But the tool will try to resolve them when combined! Below are the items with placeholders:");
                for(HierarchicalElement item:shphe.getItems()) {
                    System.out.println("NAME: "+item.getName()+" PLACEHOLDER: "+(item instanceof ClassProperty? ((ClassProperty) item).getPlaceholder(): ((ClassParameter) item).getPlaceholder()));
                }
                System.out.println();
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                System.out.println("\nEXCEPTION THROWN, SKIPPING TO NEXT FILE. Below is the message and the stacktrace:\n"+e.getMessage()+"\n"+sw.toString()+"\n");
            }
        }

        try{
            overallUML.fixPlaceholders(PlaceholderPreferenceEnum.Global);

            System.out.print("ALL UML PACKAGES ARE COMBINED!\nExporting overall UML to XMI...");
            String resultingXMI = Processor.processUML(overallUML);
            System.out.println(" OK: All placeholders are fixed!");

            System.out.print("Writing to file: Overall.xmi");
            if(!Files.exists(Paths.get("xmi-files"))) {
                System.out.print("\nCreating xmi-files directory!");
                Files.createDirectory(Paths.get("xmi-files"));
            }
            Files.write(Paths.get("xmi-files\\Overall.xmi"), resultingXMI.getBytes());
            System.out.println(" OK");

            System.out.println("File: xmi-files\\Overall.xmi is successfully created!\n");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println("\nEXCEPTION THROWN WHILE SAVING THE OVERALL UML. Below is the message and the stacktrace:\n"+e.getMessage()+"\n"+sw.toString()+"\n");
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

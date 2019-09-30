package main;

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

    public static void main(String[] args) throws Exception {
        var files = listAdaSourceFiles(".");

//        var overallUml = new UML(Paths.get(new File(".").toURI()).getParent().getFileName().toString());

        System.out.println("Found "+files.size()+" ada source files in the current directory!\n");

        for (var file : files) {
            System.out.println("Started processing: "+file.getName()+" file");
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

            // TODO check if the resultUML has placeholders, don't produce XMI yet if so.

            System.out.print("Exporting overall UML to XMI...");
            var resultingXMI = Processor.processUML(resultUml);
            System.out.println(" OK");

            System.out.print("Writing to file: "+resultUml.getName()+".xmi");
            Files.write(Paths.get(resultUml.getName()+".xmi"),resultingXMI.getBytes());
            System.out.println(" OK");

            System.out.println("File: "+resultUml.getName()+".xmi is successfully created!\n");
//            overallUml.combine(resultUml);
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

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

        var overallUml = new UML(Paths.get(new File(".").toURI()).getParent().getFileName().toString());

        System.out.println("Found "+files.size()+" ada source files in the current directory!\n");

        for (var file : files) {
            System.out.println("Started processing: "+file.getName()+" file");
            var adaFile = file;
            System.out.print("Converting to XML...");
            var adaXml = Gnat2XmlRunner.ConvertAdaCodeToXml(adaFile);
            System.out.print(" OK\nParsing compilation unit from XML...");
            var compilationUnit = AdaXmlParser.parseAndProduceCompilationUnit(adaXml);
            System.out.print(" OK\nExtracting UML concepts...");
            var resultUml = Extractor.extractHighLevelConcepts(compilationUnit);
            System.out.print(" OK\n\n");
            overallUml.combine(resultUml);
        }

        System.out.print("Exporting overall UML to XMI...");
        var resultingXMI = Processor.processUML(overallUml);
        System.out.println(" OK");

        System.out.print("Writing to file: "+overallUml.getName()+".xmi");
        System.out.println(" OK");
        Files.write(Paths.get(overallUml.getName()+".xmi"),resultingXMI.getBytes());

        System.out.println("File: "+overallUml.getName()+".xmi is successfully created!");
    }

    public static List<File> listAdaSourceFiles(String path) {
        File currentFolder = new File(path);
        return Arrays.asList(currentFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".adb") || name.endsWith(".ads"))
                    return true;
                return false;
            }
        }));
    }

}

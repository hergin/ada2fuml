package model;

import exporter.Processor;

public class EnumerationLiteral extends HierarchicalElement {

    private String id;

    public EnumerationLiteral(String name) {
        super(name);
        id = Processor.uuidGenerator();
    }

    public String getId() {
        return id;
    }
}

package model;

import exporter.Processor;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;

import java.util.ArrayList;
import java.util.List;

public class Operation {
    private List<Parameter> parameters;
    private String name;
    private VisibilityEnum visibility;
    private String id;

    public Operation(String name, VisibilityEnum visibility) {
        parameters = new ArrayList<>();
        this.name = name;
        this.visibility = visibility;
        id = Processor.uuidGenerator();
    }

    public Operation addParameter(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }


    public String getName() {
        return name;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public String getId() {
        return id;
    }
}

package model;

import exporter.Processor;
import model.enums.TypeEnum;
import model.enums.VisibilityEnum;

import java.util.ArrayList;
import java.util.List;

public class Operation extends HierarchicalElement {
    private List<Parameter> parameters;
    private VisibilityEnum visibility;
    private String id;

    public Operation(String name, VisibilityEnum visibility) {
        super(name);
        parameters = new ArrayList<>();
        this.visibility = visibility;
        id = Processor.uuidGenerator();
    }

    public Operation addParameter(Parameter parameter) {
        parameter.setParent(this);
        parameters.add(parameter);
        return this;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public String getId() {
        return id;
    }
}

package model;

// import model.enums.TypeEnum;

import model.auxiliary.HierarchicalElement;
import model.enums.VisibilityEnum;

import java.util.ArrayList;
import java.util.List;

public class Operation extends HierarchicalElement {
    private List<AbstractParameter> parameters;
    private List<Except> exceptions;
    private VisibilityEnum visibility;

    public Operation() {
        this("");
    }

    public Operation(String name) {
        super(name);
        parameters = new ArrayList<>();
        exceptions = new ArrayList<>();
        this.visibility = VisibilityEnum.Public;
    }

    public Operation(String name, VisibilityEnum visibility) {
        super(name);
        parameters = new ArrayList<>();
        exceptions = new ArrayList<>();
        this.visibility = visibility;
    }

    public Operation addParameter(AbstractParameter parameter) {
        parameter.setParent(this);
        parameters.add(parameter);
        return this;
    }

    public Operation addException(Except exception) {
        exception.setParent(this);
        exceptions.add(exception);
        return this;
    }

    public List<AbstractParameter> getParameters() {
        return parameters;
    }

    public List<Except> getExceptions() {
        return exceptions;
    }

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityEnum visibility) {
        this.visibility = visibility;
    }
}

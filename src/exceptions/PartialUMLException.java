package exceptions;

import model.UML;

import java.util.ArrayList;
import java.util.List;

public class PartialUMLException extends Exception {

    List<Exception> causes;
    UML partialUML;

    public PartialUMLException(String message, UML partialUML, List<Exception> exceptions) {
        super(message);
        this.causes = new ArrayList<>(exceptions);
        this.partialUML = partialUML;
    }

    public List<Exception> getCauses() {
        return causes;
    }

    public UML getPartialUML() {
        return partialUML;
    }
}

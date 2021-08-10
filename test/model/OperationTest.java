package model;

import model.enums.DirectionEnum;
import model.parameters.Parameter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    void cutIfNotTest() {
        Class C1 = new Class("C1");
        Operation O1 = new Operation("O1");
        Parameter PR1 = new Parameter("PR1", DirectionEnum.In);
        PR1.setReference("C2");
        O1.addParameter(PR1);
        C1.addOperation(O1);
        Operation O2 = new Operation("O2");
        Parameter PR2 = new Parameter("PR2", DirectionEnum.In);
        PR2.setReference("C1");
        O2.addParameter(PR2);
        C1.addOperation(O2);
        assertEquals(2, C1.getOperations().size());
        O2.setCondition("parameters.first.reference==parent.name");
        O2.cutIfConditionNotMet();
        assertEquals(2, C1.getOperations().size());
        O1.setCondition("parameters.first.reference==parent.name");
        O1.cutIfConditionNotMet();
        assertEquals(1, C1.getOperations().size());
    }
}
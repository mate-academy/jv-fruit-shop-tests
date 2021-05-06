package core.basesyntax.service.impl;

import org.junit.Test;

public class OperationTypeTest {
    private static final String WRONG_OPERATION = "a";

    @Test(expected = RuntimeException.class)
    public void operationTypeWithWrongInput_NotOk() {
        OperationType.getOperationType(WRONG_OPERATION);
    }
}

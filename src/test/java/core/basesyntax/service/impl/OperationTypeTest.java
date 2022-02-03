package core.basesyntax.service.impl;

import org.junit.Assert;
import org.junit.Test;

public class OperationTypeTest {
    private static final String WRONG_OPERATION = "a";
    private static final String VALID_OPERATION = "b";

    @Test(expected = RuntimeException.class)
    public void operationTypeWithWrongInput_NotOk() {
        OperationType.getOperationType(WRONG_OPERATION);
    }

    @Test
    public void operationTypeWithValidInput_OK() {
        OperationType actual = OperationType.getOperationType(VALID_OPERATION);
        OperationType expected = OperationType.BALANCE;
        Assert.assertEquals(expected, actual);
    }
}

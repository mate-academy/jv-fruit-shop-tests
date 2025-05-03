package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidOperationTypeException;
import org.junit.Test;

public class OperationTypeTest {
    private final String operationBalance = "b";
    private final String incorrectOperation = "j";

    @Test
    public void getOperation_correctOperation_Ok() {
        OperationType actual = OperationType.getOperationType(operationBalance);
        OperationType expected = OperationType.BALANCE;
        assertEquals(expected, actual);
    }

    @Test (expected = InvalidOperationTypeException.class)
    public void getOperationType_incorrectOperation_NotOk() {
        OperationType actual = OperationType.getOperationType(incorrectOperation);
    }
}

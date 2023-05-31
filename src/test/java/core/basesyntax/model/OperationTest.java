package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {
    private static final String OPERATION_BALANCE = "b";
    private static final String OPERATION_PURCHASE = "p";
    private static final String OPERATION_SUPPLY = "s";
    private static final String OPERATION_RETURN = "r";
    private static final String UNKNOWN_OPERATION_TYPE = "d";

    @Test
    public void getBalanceOperation_isOK() {
        Operation actual = Operation.getOperationEnum(OPERATION_BALANCE);
        assertEquals(Operation.BALANCE, actual);
    }

    @Test
    public void getPuchaseOperation_isOK() {
        Operation actual = Operation.getOperationEnum(OPERATION_PURCHASE);
        assertEquals(Operation.PURCHASE, actual);
    }

    @Test
    public void getReturnOperation_isOK() {
        Operation actual = Operation.getOperationEnum(OPERATION_RETURN);
        assertEquals(Operation.RETURN, actual);
    }

    @Test
    public void getSupplyOperation_isOK() {
        Operation actual = Operation.getOperationEnum(OPERATION_SUPPLY);
        assertEquals(Operation.SUPPLY, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_NotOk() {
        Operation.getOperationEnum(UNKNOWN_OPERATION_TYPE);
    }
}

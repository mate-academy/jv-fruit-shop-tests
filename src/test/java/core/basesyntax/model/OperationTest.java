package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {
    private static Operation actual;
    private static Operation expected;
    private static final String INVALID_OPERATION = "o";

    @Test
    public void getOperationFromString_balance_Ok() {
        expected = Operation.BALANCE;
        actual = Operation.getOperationFromString("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationFromString_return_Ok() {
        expected = Operation.RETURN;
        actual = Operation.getOperationFromString("r");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationFromString_purchase_Ok() {
        expected = Operation.PURCHASE;
        actual = Operation.getOperationFromString("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationFromString_supply_Ok() {
        expected = Operation.SUPPLY;
        actual = Operation.getOperationFromString("s");
        assertEquals(expected, actual);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getOperationFromString_incorrectOperation_NotOk() {
        Operation.getOperationFromString(INVALID_OPERATION);
    }
}

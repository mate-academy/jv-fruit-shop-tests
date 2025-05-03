package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {
    private static Operation actual;
    private static Operation expected;

    @Test
    public void getOperationByShortName_balance_Ok() {
        actual = Operation.getOperationByShortName("b");
        expected = Operation.BALANCE;
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByShortName_purchase_Ok() {
        actual = Operation.getOperationByShortName("p");
        expected = Operation.PURCHASE;
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByShortName_supply_Ok() {
        actual = Operation.getOperationByShortName("s");
        expected = Operation.SUPPLY;
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByShortName_return_Ok() {
        actual = Operation.getOperationByShortName("r");
        expected = Operation.RETURN;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByShortName_incorrectOperation_Ok() {
        Operation.getOperationByShortName("m");
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByShortName_incorrectOperation_null_Ok() {
        Operation.getOperationByShortName("null");
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByShortName_incorrectOperation_number_Ok() {
        Operation.getOperationByShortName("5");
    }
}

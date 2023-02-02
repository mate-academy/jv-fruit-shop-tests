package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class OperationTest {

    @Test
    public void getByCode_balanceOperation_ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = Operation.getByCode("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getSupplyEnum_ok() {
        Operation expected = Operation.SUPPLY;
        Operation actual = Operation.getByCode("s");
        assertEquals(expected, actual);
    }

    @Test
    public void getPurchaseEnum_ok() {
        Operation expected = Operation.PURCHASE;
        Operation actual = Operation.getByCode("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getReturnEnum_ok() {
        Operation expected = Operation.RETURN;
        Operation actual = Operation.getByCode("r");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnumWithNotValidData_notOk() {
        Operation actual = Operation.getByCode("y");
        assertNull(actual);
    }
}

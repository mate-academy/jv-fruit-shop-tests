package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTest {

    @BeforeClass
    public static void getByCode_balanceOperation_ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = Operation.getByFirstLetter("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getSupplyEnum_ok() {
        Operation expected = Operation.SUPPLY;
        Operation actual = Operation.getByFirstLetter("s");
        assertEquals(expected, actual);
    }

    @Test
    public void getPurchaseEnum_ok() {
        Operation expected = Operation.PURCHASE;
        Operation actual = Operation.getByFirstLetter("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getReturnEnum_ok() {
        Operation expected = Operation.RETURN;
        Operation actual = Operation.getByFirstLetter("r");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnumWithNotValidData_notOk() {
        Operation actual = Operation.getByFirstLetter("s");
        Operation expected = Operation.SUPPLY;
        assertEquals(expected, actual);
    }
}

package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static Operation operation;
    private static final int OLD_VALUE = 50;
    private static final int NEW_VALUE = 100;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operation = new BalanceOperation();
    }

    @Test
    public void calculateBalanceOperation_Ok() {
        int expected = 150;
        int actual = operation.calculateValue(OLD_VALUE, NEW_VALUE);
        assertEquals(expected, actual);
    }
}

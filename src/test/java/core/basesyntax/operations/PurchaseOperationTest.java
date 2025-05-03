package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static Operation operation;
    private static final int OLD_VALUE = 50;
    private static final int NEW_VALUE = 25;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operation = new PurchaseOperation();
    }

    @Test
    public void calculatePurchaseOperation_Ok() {
        int expected = 25;
        int actual = operation.calculateValue(OLD_VALUE, NEW_VALUE);
        assertEquals(expected, actual);
    }
}

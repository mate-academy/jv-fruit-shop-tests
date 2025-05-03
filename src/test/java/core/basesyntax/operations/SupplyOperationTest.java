package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static Operation operation;
    private static final int OLD_AMOUNT = 10;
    private static final int AMOUNT = 20;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operation = new SupplyOperation();
    }

    @Test
    public void calculateAmountTest_Ok() {
        int expected = 30;
        int actual = operation.calculateAmount(OLD_AMOUNT, AMOUNT);
        assertEquals(expected, actual);
    }
}

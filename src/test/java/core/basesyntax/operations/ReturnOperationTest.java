package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static Operation operation;
    private static final int OLD_VALUE = 50;
    private static final int NEW_VALUE = 100;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operation = new ReturnOperation();
    }

    @Test
    public void calculateReturnOperation_Ok() {
        int expected = 150;
        int actual = operation.calculateValue(OLD_VALUE, NEW_VALUE);
        assertEquals(expected, actual);
    }

}

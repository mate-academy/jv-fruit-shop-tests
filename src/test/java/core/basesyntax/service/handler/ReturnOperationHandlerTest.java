package core.basesyntax.service.handler;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final int MORE_ZERO = 5;
    private static final int ZERO = 0;
    private static OperationHandler operationHandler;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void calculateQuantity_twoOperandsMoreThanZero_isOk() {
        actual = operationHandler.calculateQuantity(MORE_ZERO, MORE_ZERO);
        expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    public void calculateQuantity_withZeroOperand_isOk() {
        actual = operationHandler.calculateQuantity(ZERO, MORE_ZERO);
        expected = 5;
        assertEquals(expected, actual);
    }
}

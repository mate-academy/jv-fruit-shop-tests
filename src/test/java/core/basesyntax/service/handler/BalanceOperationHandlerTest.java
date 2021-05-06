package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final int MORE_ZERO = 5;
    private static final int ZERO = 0;
    private static OperationHandler operationHandler;
    private int actual;
    private int expected;

    @BeforeAll
    public static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
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

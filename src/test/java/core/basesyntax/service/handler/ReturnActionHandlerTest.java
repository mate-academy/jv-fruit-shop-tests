package core.basesyntax.service.handler;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnActionHandlerTest {
    private static final int GREATER_ZERO_NUMBER = 5;
    private static final int ZERO_NUMBER = 0;
    private static ActionHandler actionHandler;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeAll() {
        actionHandler = new ReturnActionHandler();
    }

    @Test
    public void calculateQuantity_twoOperandsGreaterThanZero_returnsTrue() {
        actual = actionHandler.calculateQuantity(GREATER_ZERO_NUMBER, GREATER_ZERO_NUMBER);
        expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    public void calculateQuantity_withZeroOperand_returnsTrue() {
        actual = actionHandler.calculateQuantity(ZERO_NUMBER, GREATER_ZERO_NUMBER);
        expected = 5;
        assertEquals(expected, actual);
    }
}

package core.basesyntax.service.handler;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseActionHandlerTest {
    private static final int GREATER_ZERO_NUMBER = 5;
    private static final int ZERO_NUMBER = 0;
    private static ActionHandler actionHandler;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeAll() {
        actionHandler = new PurchaseActionHandler();
    }

    @Test
    public void calculateQuantity_availableQuantity_returnsTrue() {
        actual = actionHandler.calculateQuantity(GREATER_ZERO_NUMBER, GREATER_ZERO_NUMBER);
        expected = 0;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculateQuantity_notAvailableQuantity_RuntimeException() {
        actionHandler.calculateQuantity(ZERO_NUMBER, GREATER_ZERO_NUMBER);
    }
}

package core.basesyntax.service.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int MORE_ZERO = 5;
    private static final int ZERO = 0;
    private static OperationHandler operationHandler;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void calculateQuantity_availableQuantity_isOk() {
        actual = operationHandler.calculateQuantity(MORE_ZERO, MORE_ZERO);
        expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void calculateQuantity_notAvailableQuantity_RuntimeException() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.calculateQuantity(ZERO, MORE_ZERO));
    }
}

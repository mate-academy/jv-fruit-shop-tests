package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.PurchaseHandler;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @Test
    public void purchaseHandlerTest_getOperationAction_OK() {
        purchaseHandler = new PurchaseHandler();
        int expected = -10;
        int actual = purchaseHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}

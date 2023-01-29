package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    public void getOperationActionTest_IsGetPurchaseData_OK() {
        int expected = -10;
        int actual = purchaseHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}

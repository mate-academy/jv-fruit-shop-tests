package core.basesyntax.service.handler;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void PurchaseHandlerInitialization() {
        handler = new PurchaseOperationHandler();
    }

    @Test
    public void calculateQuantity_purchaseHandler_Ok() {
        int expected = 80;
        int actual = handler.calculateQuantity(100, 20);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculateQuantity_purchaseHandler_NegativeValueNotOk() {
        handler.calculateQuantity(2, -5);
    }

    @Test(expected = RuntimeException.class)
    public void calculateQuantity_purchaseHandler_NotEnoughFruitInStore() {
        handler.calculateQuantity(20, 23);
    }
}

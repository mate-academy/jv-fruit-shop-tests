package core.basesyntax.service.handler;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void supplyHandlerInitialization() {
        handler = new SupplyOperationHandler();
    }

    @Test
    public void calculateQuantity_supplyHandler_Ok() {
        int expected = 50;
        int actual = handler.calculateQuantity(20, 30);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculateQuantity_supplyHandler_NegativeValueNotOk() {
        handler.calculateQuantity(50, -3);
    }
}

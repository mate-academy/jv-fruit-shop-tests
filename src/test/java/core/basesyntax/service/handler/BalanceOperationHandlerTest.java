package core.basesyntax.service.handler;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void balanceHandlerInitialization() {
        handler = new BalanceOperationHandler();
    }

    @Test
    public void calculateQuantity_validData_ok() {
        int expected = 40;
        int actual = handler.calculateQuantity(10, 30);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculateQuantity_negativeValue_notOk() {
        handler.calculateQuantity(20, -30);
    }
}

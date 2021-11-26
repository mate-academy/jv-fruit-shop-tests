package core.basesyntax.strategy.operation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void getAmountToAdd_min_ok() {
        Integer expected = 0;
        Integer actual = operationHandler.getAmountToAdd(0);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getAmountToAdd_max_ok() {
        Integer expected = -Integer.MAX_VALUE;
        Integer actual = operationHandler.getAmountToAdd(Integer.MAX_VALUE);
        Assert.assertEquals(expected,actual);
    }
}

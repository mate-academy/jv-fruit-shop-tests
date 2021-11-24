package core.basesyntax.strategy.operation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void getAmountToAdd_min_ok() {
        Integer expected = 0;
        Integer actual = operationHandler.getAmountToAdd(0);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getAmountToAdd_max_ok() {
        Integer expected = Integer.MAX_VALUE;
        Integer actual = operationHandler.getAmountToAdd(2147483647);
        Assert.assertEquals(expected,actual);
    }
}

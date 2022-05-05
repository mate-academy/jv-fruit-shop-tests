package strategy;

import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyTest {
    private static Strategy strategy;

    @BeforeClass
    public static void setUp() {
        strategy = new Strategy();
    }

    @Test
    public void invalidOutput_Ok() {
        OperationHandler actual = strategy.get("y");
        assertNull(actual);
    }

    @Test
    public void validOutputPurchaseOperationHandler_Ok() {
        Class<? extends OperationHandler> actual = strategy.get("p").getClass();
        Assert.assertEquals(PurchaseOperationHandler.class, actual);
    }

    @Test
    public void validOutputSupplyOperationHandler_Ok() {
        Class<? extends OperationHandler> actual = strategy.get("s").getClass();
        Assert.assertEquals(SupplyOperationHandler.class, actual);
    }

    @Test
    public void validOutputReturnOperationHandler_Ok() {
        Class<? extends OperationHandler> actual = strategy.get("r").getClass();
        Assert.assertEquals(ReturnOperationHandler.class, actual);
    }

    @Test
    public void validOutputBalanceOperationHandler_Ok() {
        Class<? extends OperationHandler> actual = strategy.get("b").getClass();
        Assert.assertEquals(BalanceOperationHandler.class, actual);
    }
}

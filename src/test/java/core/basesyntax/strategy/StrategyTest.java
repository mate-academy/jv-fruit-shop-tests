package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDaoImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyTest {
    private static Strategy strategy;

    @BeforeClass
    public static void beforeClass() {
        strategy = new Strategy(new StorageDaoImpl());
    }

    @Test
    public void get_purchaseValidOutput_Ok() {
        Class<?> expected = PurchaseHandlerImpl.class;
        Class<?> actual = strategy.get("p").getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_balanceValidOutput_Ok() {
        Class<?> expected = BalanceHandler.class;
        Class<?> actual = strategy.get("b").getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_supplyValidOutput_Ok() {
        Class<?> expected = SupplyHandlerImpl.class;
        Class<?> actual = strategy.get("s").getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_nonExistHandler_NoOk() {
        strategy.get(null);
    }
}

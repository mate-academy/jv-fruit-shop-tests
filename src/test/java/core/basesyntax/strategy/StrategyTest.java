package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyTest {
    private static StorageDao dao;
    private static OperationHandler supplyHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler balanceHandler;
    private static Strategy strategy;
    private static Map<String, OperationHandler> operation;

    @BeforeClass
    public static void beforeClass() {
        dao = new StorageDaoImpl();
        supplyHandler = new SupplyHandlerImpl(dao);
        purchaseHandler = new PurchaseHandlerImpl(dao);
        balanceHandler = new BalanceHandler(dao);
        strategy = new Strategy(dao);
        operation = new Strategy(dao).getMap();
    }

    @Test
    public void get_validOutput_Ok() {
        Class<?> expected = PurchaseHandlerImpl.class;
        Class<?> actual = strategy.get("p").getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMap_supplyHandler_Ok() {
        Map<String, OperationHandler> actual = operation;
        Assert.assertEquals(actual.get("s").getClass(), supplyHandler.getClass());
        Assert.assertTrue(actual.containsKey("s"));
    }

    @Test
    public void getMap_balanceHandler_Ok() {
        Map<String, OperationHandler> actual = operation;
        Assert.assertEquals(actual.get("p").getClass(), purchaseHandler.getClass());
        Assert.assertTrue(actual.containsKey("p"));
    }

    @Test
    public void getMap_purchaseHandler_Ok() {
        Map<String, OperationHandler> actual = operation;
        Assert.assertEquals(actual.get("b").getClass(), balanceHandler.getClass());
        Assert.assertTrue(actual.containsKey("b"));
    }

    @Test
    public void getMap_SecondSupplyHandler_Ok() {
        Map<String, OperationHandler> actual = operation;
        Assert.assertEquals(actual.get("r").getClass(), supplyHandler.getClass());
        Assert.assertTrue(actual.containsKey("r"));
    }

    @Test
    public void get_invalidOutput_NotOk() {
        OperationHandler actual = strategy.get("t");
        Assert.assertNull(actual);
    }
}

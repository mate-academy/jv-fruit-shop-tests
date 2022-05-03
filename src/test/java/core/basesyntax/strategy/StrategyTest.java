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
        OperationHandler expected = purchaseHandler;
        OperationHandler actual = strategy.get("p");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMap_validOutput_Ok() {
        Map<String, OperationHandler> actual = operation;
        Assert.assertEquals(actual.get("s"), supplyHandler);
        Assert.assertEquals(actual.get("p"), purchaseHandler);
        Assert.assertEquals(actual.get("b"), balanceHandler);
        Assert.assertEquals(actual.get("r"), supplyHandler);
        Assert.assertTrue(actual.containsKey("p"));
        Assert.assertTrue(actual.containsKey("s"));
        Assert.assertTrue(actual.containsKey("b"));
        Assert.assertTrue(actual.containsKey("r"));
        Assert.assertEquals(actual.size(), 4);
    }

    @Test
    public void get_invalidOutput_Ok() {
        OperationHandler expected = balanceHandler;
        OperationHandler actual = strategy.get("r");
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void getMap_invalidOutput_NotOk() {
        Map<String, OperationHandler> actual = operation;
        Assert.assertFalse(actual.containsKey(null));
        Assert.assertFalse(actual.containsKey("e"));
        Assert.assertFalse(actual.containsKey(""));
        Assert.assertFalse(actual.containsKey(" "));
        Assert.assertNotEquals(actual.get("r"), balanceHandler);
    }
}

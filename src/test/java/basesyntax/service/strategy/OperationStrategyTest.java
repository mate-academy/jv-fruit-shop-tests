package basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.service.strategy.handlers.BalanceOperationHandler;
import basesyntax.service.strategy.handlers.OperationHandler;
import basesyntax.service.strategy.handlers.PurchaseOperationHandler;
import basesyntax.service.strategy.handlers.ReturnOperationHandler;
import basesyntax.service.strategy.handlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static StorageDao storageDao;
    private static OperationStrategy operationStrategy;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler returnOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
        returnOperationHandler = new ReturnOperationHandler(storageDao);
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        supplyOperationHandler = new SupplyOperationHandler(storageDao);
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", balanceOperationHandler);
        map.put("r", returnOperationHandler);
        map.put("s", supplyOperationHandler);
        map.put("p", purchaseOperationHandler);
        operationStrategy = new OperationStrategyImpl(map);
    }

    @Test
    public void get_balanceOperationHandler_OK() {
        OperationHandler expected = balanceOperationHandler;
        OperationHandler actual = operationStrategy.get("b");
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperationHandler_OK() {
        OperationHandler expected = purchaseOperationHandler;
        OperationHandler actual = operationStrategy.get("p");
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyOperationHandler_OK() {
        OperationHandler expected = supplyOperationHandler;
        OperationHandler actual = operationStrategy.get("s");
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnOperationHandler_OK() {
        OperationHandler expected = returnOperationHandler;
        OperationHandler actual = operationStrategy.get("r");
        assertEquals(expected, actual);
    }
}

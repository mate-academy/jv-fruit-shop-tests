package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.service.strategy.handlers.OperationHandler;
import core.basesyntax.service.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.service.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.service.strategy.handlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler returnOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
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
    public void get_balanceOperationHandler_Ok() {
        OperationHandler expected = balanceOperationHandler;
        OperationHandler actual = operationStrategy.get("b");
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperationHandler_Ok() {
        OperationHandler expected = purchaseOperationHandler;
        OperationHandler actual = operationStrategy.get("p");
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyOperationHandler_Ok() {
        OperationHandler expected = supplyOperationHandler;
        OperationHandler actual = operationStrategy.get("s");
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnOperationHandler_Ok() {
        OperationHandler expected = returnOperationHandler;
        OperationHandler actual = operationStrategy.get("r");
        assertEquals(expected, actual);
    }
}

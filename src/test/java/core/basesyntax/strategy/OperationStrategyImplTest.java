package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperationHandler(storageDao));
        map.put("r", new ReturnOperationHandler(storageDao));
        map.put("s", new SupplyOperationHandler(storageDao));
        map.put("p", new PurchaseOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(map);
    }

    @Test
    public void get_balanceOperationHandler_Ok() {
        Class<?> expected = BalanceOperationHandler.class;
        Class<?> actual = operationStrategy.get("b").getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperationHandler_Ok() {
        Class<?> expected = PurchaseOperationHandler.class;
        Class<?> actual = operationStrategy.get("p").getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyOperationHandler_Ok() {
        Class<?> expected = SupplyOperationHandler.class;
        Class<?> actual = operationStrategy.get("s").getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnOperationHandler_Ok() {
        Class<?> expected = ReturnOperationHandler.class;
        Class<?> actual = operationStrategy.get("r").getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_invalidOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get("z");
        assertNull(actual);
    }
}

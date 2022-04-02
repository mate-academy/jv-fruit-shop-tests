package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.operation.AddOperationHandlerImpl;
import core.basesyntax.service.operation.BalanceOperationHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandlerImpl;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplTest {
    private static final StorageDao fruitStorageDao = new StorageDaoImpl();
    private static Strategy strategy;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static AddOperationHandlerImpl addOperationHandler;
    private static PurchaseOperationHandlerImpl purchaseOperationHandler;
    private static BalanceOperationHandlerImpl balanceOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        strategy = new StrategyImpl(fruitStorageDao);
        operationHandlerMap = new StrategyImpl(fruitStorageDao).getMap();
        addOperationHandler = new AddOperationHandlerImpl(fruitStorageDao);
        purchaseOperationHandler = new PurchaseOperationHandlerImpl(fruitStorageDao);
        balanceOperationHandler = new BalanceOperationHandlerImpl(fruitStorageDao);
    }

    @Test
    public void getMap_validOutput_Ok() {
        Map<String, OperationHandler> actual = operationHandlerMap;
        assertEquals(actual.get("b"), balanceOperationHandler);
        assertEquals(actual.get("r"), addOperationHandler);
        assertEquals(actual.get("s"), addOperationHandler);
        assertEquals(actual.get("p"), purchaseOperationHandler);
        assertTrue(actual.containsKey("b"));
        assertTrue(actual.containsKey("r"));
        assertTrue(actual.containsKey("p"));
        assertTrue(actual.containsKey("s"));
        assertEquals(actual.size(), 4);
    }

    @Test
    public void getMap_invalidOutput_NotOk() {
        Map<String, OperationHandler> actual = operationHandlerMap;
        assertFalse(actual.containsKey("n"));
        assertFalse(actual.containsKey(" "));
        assertFalse(actual.containsKey(null));
        assertNotEquals(actual.get("r"), balanceOperationHandler);
    }

    @Test
    public void get_validOutput_Ok() {
        OperationHandler expected = balanceOperationHandler;
        OperationHandler actual = strategy.get("b");
        assertEquals(expected, actual);
    }

    @Test
    public void get_invalidOutput_Ok() {
        OperationHandler expected = purchaseOperationHandler;
        OperationHandler actual = strategy.get("r");
        assertNotEquals(expected, actual);
    }
}

package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReturnTransactionImplTest {
    private static final Map<String, Integer> testStorageMap = new HashMap<>();
    private static OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        operationHandler = new ReturnTransactionImpl();
        Storage.setFruitStore(testStorageMap);
    }

    @Test
    public void handle_returnTransaction_OK() {
        testStorageMap.put("banana", 30);
        Storage.setFruitStore(testStorageMap);
        operationHandler.handle("banana", 10);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(40));
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitValue_null_NotOK() {
        operationHandler.handle(null, 30);
    }

    @Test(expected = RuntimeException.class)
    public void handle_cantFindFruitInMap_NotOK() {
        testStorageMap.put("banana", 30);
        Storage.setFruitStore(testStorageMap);
        operationHandler.handle("coconut", 2);
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitQuantityLessThanMin_NotOK() {
        int wrongQuantity = -30;
        operationHandler.handle("banana", wrongQuantity);
    }
}

package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTransactionImplTest {
    private static final Map<String, Integer> testStorageMap = new HashMap<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.setFruitStore(testStorageMap);
    }

    @Test
    public void handle_purchaseTransaction_OK() {
        testStorageMap.put("banana", 30);
        Storage.setFruitStore(testStorageMap);
        OperationHandler operationHandler = new PurchaseTransactionImpl();
        operationHandler.handle("banana", 10);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(20));
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitValue_null_NotOK() {
        String wrongFruit = null;
        OperationHandler operationHandler = new PurchaseTransactionImpl();
        operationHandler.handle(wrongFruit, 30);
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitQuantityLessThanMin_NotOK() {
        testStorageMap.put("banana", 30);
        Storage.setFruitStore(testStorageMap);
        int wrongQuantity = -31;
        OperationHandler operationHandler = new PurchaseTransactionImpl();
        operationHandler.handle("banana", wrongQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void handle_cantFindFruitInMap_NotOK() {
        testStorageMap.put("banana", 30);
        Storage.setFruitStore(testStorageMap);
        OperationHandler operationHandler = new PurchaseTransactionImpl();
        operationHandler.handle("coconut", 2);
    }
}

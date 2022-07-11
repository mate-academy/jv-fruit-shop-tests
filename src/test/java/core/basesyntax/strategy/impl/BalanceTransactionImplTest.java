package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceTransactionImplTest {
    private static final Map<String, Integer> testStorageMap = new HashMap<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.setFruitStore(testStorageMap);
    }

    @Test
    public void handle_balanceTransaction_OK() {
        OperationHandler operationHandler = new BalanceTransactionImpl();
        operationHandler.handle("banana", 30);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(30));
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitValue_null_NotOK() {
        String wrongFruit = null;
        OperationHandler operationHandler = new BalanceTransactionImpl();
        operationHandler.handle(wrongFruit, 30);
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitQuantityLessThanMin_NotOK() {
        int wrongQuantity = -30;
        OperationHandler operationHandler = new BalanceTransactionImpl();
        operationHandler.handle("banana", wrongQuantity);
    }
}

package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class BalanceTransactionImplTest {
    @Test
    public void balanceTransaction_OK() {
        Map<String, Integer> testStorageMap = new HashMap<>();
        Storage.setFruitStore(testStorageMap);
        OperationHandler operationHandler = new BalanceTransactionImpl();
        operationHandler.handle("banana", 30);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(30));
    }
}

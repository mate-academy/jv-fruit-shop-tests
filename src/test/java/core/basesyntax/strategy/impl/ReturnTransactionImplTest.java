package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReturnTransactionImplTest {
    @Test
    public void returnTransaction_OK() {
        Map<String, Integer> testStorageMap = new HashMap<>();
        testStorageMap.put("banana", 30);
        Storage.setFruitStore(testStorageMap);
        OperationHandler operationHandler = new ReturnTransactionImpl();
        operationHandler.handle("banana", 10);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(40));
    }
}

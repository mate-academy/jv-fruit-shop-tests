package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class SupplyTransactionImplTest {
    @Test
    public void supplyTransaction_OK() {
        Map<String, Integer> testStorageMap = new HashMap<>();
        testStorageMap.put("banana", 30);
        Storage.setFruitStore(testStorageMap);
        OperationHandler operationHandler = new SupplyTransactionImpl();
        operationHandler.handle("banana", 10);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(40));
    }
}

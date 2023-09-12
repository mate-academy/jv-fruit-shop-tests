package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    @Test
    void purchaseFruits_Ok() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);
        balanceOperationHandler = new PurchaseOperationHandler();
        balanceOperationHandler.processOperation("Banana", 5);
        balanceOperationHandler.processOperation("Apple", 10);
        balanceOperationHandler.processOperation("Grape", 1);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 5);
        expectedStorage.put("Apple", 10);
        expectedStorage.put("Grape", 2);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void purchaseMoreFruitsThenHave_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.processOperation("Banana", 11));
    }

    @Test
    void purchaseAllFruitsThenHave_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);
        balanceOperationHandler = new PurchaseOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 0);
        expectedStorage.put("Apple", 0);
        expectedStorage.put("Grape", 0);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}

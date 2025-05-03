package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static OperationHandler purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperationHandler();
    }

    @Test
    void purchaseFruits_PurchaseOperation_Ok() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);

        purchaseOperation.processOperation("Banana", 5);
        purchaseOperation.processOperation("Apple", 10);
        purchaseOperation.processOperation("Grape", 1);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 5);
        expectedStorage.put("Apple", 10);
        expectedStorage.put("Grape", 2);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void purchaseMoreFruitsThenHave_PurchaseOperation_NotOk() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.processOperation("Banana", 11));
    }

    @Test
    void purchaseAllFruitsThenHave_PurchaseOperation_NotOk() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);
        purchaseOperation.processOperation("Banana", 10);
        purchaseOperation.processOperation("Apple", 20);
        purchaseOperation.processOperation("Grape", 3);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 0);
        expectedStorage.put("Apple", 0);
        expectedStorage.put("Grape", 0);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void purchaseFruitsWithIncorrectValue_PurchaseOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperation.processOperation("Banana", -10));
    }

    @Test
    void purchaseFruitsWithNullValue_PurchaseOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperation.processOperation(null, null));
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}

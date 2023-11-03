package core.basesyntax.strategy.handlers;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImp;
import core.basesyntax.model.GoodsOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static Map<String, Integer> storage;
    private static StorageDao storageDao;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static GoodsOperation purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new HashMap<>();
        storageDao = new StorageDaoImp(storage);
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        purchaseOperation = new GoodsOperation(GoodsOperation.TransactionType.PURCHASE,
                "banana", 24);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handleOperation_validOperations_ok() {
        int stocksQuantity = 73;
        storage.put("banana", stocksQuantity);
        purchaseOperationHandler.handleOperation(purchaseOperation);
        Integer actual = storage.get(purchaseOperation.getItem());
        Integer expected = stocksQuantity - purchaseOperation.getQuantity();
        Assertions.assertEquals(expected, actual,
                String.format("Expected quantity of %s not equals actual",
                        purchaseOperation.getItem()));
    }

    @Test
    void handleOperation_nullItemName_notOk() {
        purchaseOperation = new GoodsOperation(purchaseOperation.getTransactionType(),
                null,
                purchaseOperation.getQuantity());
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleOperation(purchaseOperation),
                "Goods name can't be null");
    }

    @Test
    void handleOperation_stocksLack_notOk() {
        int stocksQuantity = 1;
        storage.put("banana", stocksQuantity);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleOperation(purchaseOperation),
                "Quantity of purchase operation can't be more than stock of goods");
    }
}

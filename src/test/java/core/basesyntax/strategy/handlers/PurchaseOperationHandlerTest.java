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
    private static final String BANANA = "banana";
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
                BANANA, 24);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handleOperation_validOperations_ok() {
        int stocksQuantity = 73;
        storage.put(BANANA, stocksQuantity);
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
        String expectedMessage = "can't be null";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handleOperation(purchaseOperation);
        });
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage),
                "Exception message must contain string: " + expectedMessage);
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

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

class SupplyOperationHandlerTest {
    private static final String BANANA = "banana";
    private static Map<String, Integer> storage;
    private static StorageDao storageDao;
    private static SupplyOperationHandler supplyOperationHandler;
    private static GoodsOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new HashMap<>();
        storageDao = new StorageDaoImp(storage);
        supplyOperationHandler = new SupplyOperationHandler(storageDao);
        supplyOperation = new GoodsOperation(GoodsOperation.TransactionType.SUPPLY,
                BANANA, 50);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handleOperation_validOperations_ok() {
        supplyOperationHandler.handleOperation(supplyOperation);
        Integer actual = storage.get(supplyOperation.getItem());
        Integer expected = supplyOperation.getQuantity();
        Assertions.assertEquals(expected, actual,
                String.format("Expected quantity of %s not equals actual",
                        supplyOperation.getItem()));
    }

    @Test
    void handleOperation_nullItemName_notOk() {
        supplyOperation = new GoodsOperation(supplyOperation.getTransactionType(),
                null,
                supplyOperation.getQuantity());
        String expectedMessage = "can't be null";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            supplyOperationHandler.handleOperation(supplyOperation);
        });
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage),
                "Exception message must contain string: " + expectedMessage);
    }
}

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

class ReturnOperationHandlerTest {
    private static final String BANANA = "banana";
    private static Map<String, Integer> storage;
    private static StorageDao storageDao;
    private static ReturnOperationHandler returnOperationHandler;
    private static GoodsOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new HashMap<>();
        storageDao = new StorageDaoImp(storage);
        returnOperationHandler = new ReturnOperationHandler(storageDao);
        returnOperation = new GoodsOperation(GoodsOperation.TransactionType.RETURN,
                BANANA, 50);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handleOperation_validOperations_ok() {
        returnOperationHandler.handleOperation(returnOperation);
        Integer actual = storage.get(returnOperation.getItem());
        Integer expected = returnOperation.getQuantity();
        Assertions.assertEquals(expected, actual,
                String.format("Expected quantity of %s not equals actual",
                        returnOperation.getItem()));
    }

    @Test
    void handleOperation_nullItemName_notOk() {
        returnOperation = new GoodsOperation(returnOperation.getTransactionType(),
                null,
                returnOperation.getQuantity());
        String expectedMessage = "can't be null";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            returnOperationHandler.handleOperation(returnOperation);
        });
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage),
                "Exception message must contain string: " + expectedMessage);
    }
}

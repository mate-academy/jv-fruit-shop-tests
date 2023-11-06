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

class BalanceOperationHandlerTest {
    private static final String BANANA = "banana";

    private static Map<String, Integer> storage;
    private static StorageDao storageDao;
    private static BalanceOperationHandler balanceOperationHandler;
    private static GoodsOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new HashMap<>();
        storageDao = new StorageDaoImp(storage);
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
        balanceOperation = new GoodsOperation(GoodsOperation.TransactionType.BALANCE,
                BANANA, 50);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handleOperation_validOperations_ok() {
        balanceOperationHandler.handleOperation(balanceOperation);
        Integer actual = storage.get(balanceOperation.getItem());
        Integer expected = balanceOperation.getQuantity();
        Assertions.assertEquals(expected, actual,
                String.format("Expected quantity of %s not equals actual",
                        balanceOperation.getItem()));
    }

    @Test
    void handleOperation_nullItemName_notOk() {
        balanceOperation = new GoodsOperation(balanceOperation.getTransactionType(),
                null,
                balanceOperation.getQuantity());
        String expectedMessage = "can't be null";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            balanceOperationHandler.handleOperation(balanceOperation);
        });
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage),
                "Exception message must contain string: " + expectedMessage);
    }
}

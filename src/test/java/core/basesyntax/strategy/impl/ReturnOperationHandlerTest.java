package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(storageDao);
    }

    @AfterEach
    void tearDown() {
        Storage.clearAllFruitsQuantities();
    }

    @Test
    void constructor_nullStorageDao_notOk() {
        assertThrows(RuntimeException.class,
                () -> new BalanceOperationHandler(null));
    }

    @Test
    void handleTransaction_validTransaction_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 25);
        returnOperationHandler.handleTransaction(fruitTransaction);
        int actual = storageDao.getFruitQuantity(fruitTransaction.getFruit());
        assertEquals(25, actual);
    }

    @Test
    void handleTransaction_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.handleTransaction(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -1)));
        assertTrue(exception.getMessage().contains("Return quantity must be positive"));

    }

    @Test
    void handleTransaction_zeroQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.handleTransaction(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0)));
        assertTrue(exception.getMessage().contains("Return quantity must be positive"));

    }
}

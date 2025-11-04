package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.StorageDao;
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
        storageDao = mock(StorageDao.class);
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
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 25);
        when(storageDao.getFruitQuantity("apple")).thenReturn(5);
        returnOperationHandler.handleTransaction(fruitTransaction);
        verify(storageDao, times(1)).addFruitQuantity("apple", 25);
    }

    @Test
    void handleTransaction_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.handleTransaction(
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", -1)));
        assertTrue(exception.getMessage().contains("Return quantity must be positive"));

    }

    @Test
    void handleTransaction_zeroQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.handleTransaction(
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 0)));
        assertTrue(exception.getMessage().contains("Return quantity must be positive"));

    }
}

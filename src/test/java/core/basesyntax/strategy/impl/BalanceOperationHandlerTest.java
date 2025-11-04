package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = mock(StorageDao.class);
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
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
        balanceOperationHandler.handleTransaction(fruitTransaction);
        verify(storageDao, times(1)).setFruitQuantity("apple", 25);
    }

    @Test
    void handleTransaction_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.handleTransaction(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -1)));
        assertEquals("Quantity cannot be negative", exception.getMessage());
        verify(storageDao, times(0)).setFruitQuantity("apple", -1);
    }
}

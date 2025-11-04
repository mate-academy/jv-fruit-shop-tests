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

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = mock(StorageDao.class);
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
    }

    @AfterEach
    void tearDown() {
        Storage.clearAllFruitsQuantities();
    }

    @Test
    void constructor_nullStorageDao_notOk() {
        assertThrows(RuntimeException.class,
                () -> new PurchaseOperationHandler(null));
    }

    @Test
    void handleTransaction_validTransaction_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 25);
        when(storageDao.getFruitQuantity("apple")).thenReturn(25);
        purchaseOperationHandler.handleTransaction(fruitTransaction);
        verify(storageDao, times(1)).deductFruitQuantity("apple", 25);
    }

    @Test
    void handleTransaction_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", -12);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Purchase quantity must be positive"));
    }

    @Test
    void handleTransaction_zeroQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 0);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Purchase quantity must be positive"));
    }

    @Test
    void handleTransaction_quantityBiggerThanBalance_notOk() {
        storageDao.setFruitQuantity("apple", 5);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Not enough"));
    }
}

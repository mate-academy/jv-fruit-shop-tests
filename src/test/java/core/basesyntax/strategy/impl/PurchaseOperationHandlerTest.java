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

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
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
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 25);
        storageDao.setFruitQuantity("apple", 25);
        purchaseOperationHandler.handleTransaction(fruitTransaction);
        int actual = storageDao.getFruitQuantity("apple");
        assertEquals(0, actual);
    }

    @Test
    void handleTransaction_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -12);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Purchase quantity must be positive"));
    }

    @Test
    void handleTransaction_zeroQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Purchase quantity must be positive"));
    }

    @Test
    void handleTransaction_quantityBiggerThanBalance_notOk() {
        storageDao.setFruitQuantity("apple", 5);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Not enough"));
    }
}

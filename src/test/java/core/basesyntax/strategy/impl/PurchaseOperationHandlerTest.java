package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.db.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private StorageDao storageDao;
    private PurchaseOperationHandler purchaseOperationHandler;
    private FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE,
            "apple", 20);

    @BeforeEach
    void beforeEach() {
        storageDao = mock(StorageDao.class);
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
    }

    @Test
    void execute_notExistingFruit_notOk() {
        when(storageDao.checkFruit("apple")).thenReturn(false);
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.execute(fruitTransaction));
    }

    @Test
    void execute_negativeBalance_notOk() {
        when(storageDao.checkFruit("apple")).thenReturn(true);
        when(storageDao.actualQuantity("apple")).thenReturn(5);
        assertThrows(IllegalArgumentException.class, () ->
                purchaseOperationHandler.execute(fruitTransaction));
    }

    @Test
    void execute_existingFruit_ok() {
        when(storageDao.checkFruit("apple")).thenReturn(true);
        when(storageDao.actualQuantity("apple")).thenReturn(25);
        purchaseOperationHandler.execute(fruitTransaction);
        verify(storageDao).updateQuantity("apple", 5);
    }

}

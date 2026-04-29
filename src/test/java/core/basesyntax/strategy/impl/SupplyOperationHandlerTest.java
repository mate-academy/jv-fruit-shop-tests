package core.basesyntax.strategy.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.db.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private StorageDao storageDao;
    private SupplyOperationHandler supplyOperationHandler;
    private FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "apple", 20);

    @BeforeEach
    void beforeEach() {
        storageDao = mock(StorageDao.class);
        supplyOperationHandler = new SupplyOperationHandler(storageDao);
    }

    @Test
    void execute_notExistingFruit_ok() {
        when(storageDao.checkFruit("apple")).thenReturn(false);
        supplyOperationHandler.execute(fruitTransaction);
        verify(storageDao).add(fruitTransaction);
    }

    @Test
    void execute_existingFruit_ok() {
        when(storageDao.checkFruit("apple")).thenReturn(true);
        when(storageDao.actualQuantity("apple")).thenReturn(25);
        supplyOperationHandler.execute(fruitTransaction);
        verify(storageDao).updateQuantity("apple", 45);
    }

}

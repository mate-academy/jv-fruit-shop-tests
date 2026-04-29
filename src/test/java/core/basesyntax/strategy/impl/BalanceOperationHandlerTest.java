package core.basesyntax.strategy.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.db.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private StorageDao storageDao;
    private BalanceOperationHandler balanceOperationHandler;
    private FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE,
            "apple", 20);

    @BeforeEach
    void beforeEach() {
        storageDao = mock(StorageDao.class);
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
    }

    @Test
    void execute_existingFruit_ok() {
        when(storageDao.checkFruit("apple")).thenReturn(true);
        balanceOperationHandler.execute(fruitTransaction);
        verify(storageDao).updateQuantity("apple", 20);
    }

    @Test
    void execute_nonExistingFruit_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 20);
        when(storageDao.checkFruit("apple")).thenReturn(false);
        balanceOperationHandler.execute(fruitTransaction);
        verify(storageDao).add(fruitTransaction);

    }
}

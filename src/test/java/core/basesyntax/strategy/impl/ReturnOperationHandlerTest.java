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

class ReturnOperationHandlerTest {
    private StorageDao storageDao;
    private ReturnOperationHandler returnOperationHandler;
    private FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "apple", 20);

    @BeforeEach
    void beforeEach() {
        storageDao = mock(StorageDao.class);
        returnOperationHandler = new ReturnOperationHandler(storageDao);
    }

    @Test
    void execute_notExistingFruit_notOk() {
        when(storageDao.checkFruit("apple")).thenReturn(false);
        assertThrows(RuntimeException.class,
                () -> returnOperationHandler.execute(fruitTransaction));
    }

    @Test
    void execute_existingFruit_ok() {
        when(storageDao.checkFruit("apple")).thenReturn(true);
        when(storageDao.actualQuantity("apple")).thenReturn(25);
        returnOperationHandler.execute(fruitTransaction);
        verify(storageDao).updateQuantity("apple", 45);
    }
}

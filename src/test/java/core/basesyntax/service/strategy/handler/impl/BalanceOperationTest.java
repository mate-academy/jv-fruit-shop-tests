package core.basesyntax.service.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransaction bananaTransaction;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();

        bananaTransaction = new FruitTransaction();
        bananaTransaction.setQuantity(23);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setOperation("b");
    }

    @Test
    public void fruitTransaction_IsNull_NotOk() {
        OperationHandler operationHandler = new BalanceOperation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(null, storage)
        );

        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void storage_IsNull_NotOk() {
        OperationHandler operationHandler = new BalanceOperation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(bananaTransaction, null)
        );

        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void addFruitsToStorage_Ok() {
        OperationHandler operationHandler = new BalanceOperation();

        operationHandler.handle(bananaTransaction, storage);

        assertTrue(storage.getAll().containsKey("banana"));
        assertEquals(23, storage.get("banana"));
    }
}

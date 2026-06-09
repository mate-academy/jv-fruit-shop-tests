package core.basesyntax.service.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitTransaction bananaTransaction;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();

        bananaTransaction = new FruitTransaction();
        bananaTransaction.setQuantity(23);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setOperation("r");
    }

    @Test
    public void fruitTransaction_IsNull_NotOk() {
        OperationHandler operationHandler = new ReturnOperation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(null, storage)
        );
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void storage_IsNull_NotOk() {
        OperationHandler operationHandler = new ReturnOperation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(bananaTransaction, null)
        );
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void returnFruitsToStorage_Ok() {
        OperationHandler operationHandler = new ReturnOperation();
        storage.put("banana", 10);
        operationHandler.handle(bananaTransaction, storage);
        assertEquals(33, storage.get("banana"));
    }
}

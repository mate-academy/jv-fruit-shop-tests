package core.basesyntax.service.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitTransaction bananaTransaction;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();

        bananaTransaction = new FruitTransaction();
        bananaTransaction.setQuantity(30);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setOperation("s");
    }

    @Test
    public void fruitTransaction_IsNull_NotOk() {
        OperationHandler operationHandler = new SupplyOperation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(null, storage)
        );
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void storage_IsNull_NotOk() {
        OperationHandler operationHandler = new SupplyOperation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(bananaTransaction, null)
        );
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void supplyFruitsToStorage_Ok() {
        OperationHandler operationHandler = new SupplyOperation();
        storage.put("banana", 20);
        operationHandler.handle(bananaTransaction, storage);
        assertEquals(50, storage.get("banana"));
    }
}

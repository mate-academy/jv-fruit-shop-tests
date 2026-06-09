package core.basesyntax.service.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void fruitTransaction_IsNull_NotOk() {
        OperationHandler operationHandler = new PurchaseOperation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(null, storage)
        );

        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void storage_IsNull_NotOk() {
        OperationHandler operationHandler = new PurchaseOperation();

        FruitTransaction bananaTransaction = new FruitTransaction();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(bananaTransaction, null)
        );

        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void changeQuantity_InStorage_Ok() {
        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setQuantity(30);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setOperation("p");

        storage.put("banana", 100);

        OperationHandler operationHandler = new PurchaseOperation();
        operationHandler.handle(bananaTransaction, storage);

        assertTrue(storage.getAll().containsKey("banana"));
        assertEquals(70, storage.get("banana"));
    }

    @Test
    public void notEnoughFruits_InStorage_NotOk() {
        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setQuantity(50);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setOperation("p");

        OperationHandler operationHandler = new PurchaseOperation();
        storage.put("banana", 20);

        assertTrue(storage.getAll().containsKey("banana"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle(bananaTransaction, storage)
        );
        assertEquals("not enough fruits", exception.getMessage());
    }
}

package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;

class PurchaseOperationHandlerTest {
    private Storage storage;
    private OperationHandler handlerOperation;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        storage.put("apple", 40);
        handlerOperation =
                new PurchaseOperationHandler();
        transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
    }

    @Test
    void handler_validValueOk() {
        handlerOperation.handler(transaction, storage);
        assertEquals(30, storage.getAll().get("apple"));
    }

    @Test
    void handler_fruitIsNull_NotOk() {
        transaction.setFruit(null);
        assertThrows(IllegalArgumentException.class,
                () -> handlerOperation.handler(transaction, storage));
    }

    @Test
    void handler_quantityIsNegative_NotOk() {
        assertThrows(RuntimeException.class,
                () -> handlerOperation.handler(new FruitTransaction(
                                FruitTransaction.Operation.PURCHASE, "apple", -11),
                        storage));
    }
}

package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;

class BalanceOperationHandlerTest {
    private Storage storage;
    private OperationHandler handlerOperation;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handlerOperation =
                new BalanceOperationHandler();
        transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 11);
    }

    @Test
    void handler_validValue_Ok() {
        handlerOperation.handler(transaction,storage);
        assertEquals(11, storage.getAll().get("apple"));
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
                        FruitTransaction.Operation.BALANCE, "apple", -11),
                        storage));
    }
}

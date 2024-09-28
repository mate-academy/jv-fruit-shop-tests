package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler();
        Storage.clear();
    }

    @Test
    void apply_newFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 50);
        handler.apply(transaction);
        assertEquals(50, Storage.getFruitQuantity("banana"));
    }

    @Test
    void apply_existingFruit_overwrite_ok() {
        Storage.setFruitQuantity("banana", 30);
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 50);
        handler.apply(transaction);
        assertEquals(50, Storage.getFruitQuantity("banana"));
    }
}

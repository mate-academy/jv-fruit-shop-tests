package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SupplyOperationHandler();
        Storage.clear();
    }

    @Test
    void apply_newFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 40);
        handler.apply(transaction);
        assertEquals(40, Storage.getFruitQuantity("apple"));
    }

    @Test
    void apply_existingFruit_ok() {
        Storage.setFruitQuantity("apple", 20);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 40);
        handler.apply(transaction);
        assertEquals(60, Storage.getFruitQuantity("apple"));
    }
}

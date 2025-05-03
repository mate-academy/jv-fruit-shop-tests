package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String ORANGE = "orange";
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ReturnOperationHandler();
        Storage.clear();
    }

    @Test
    void apply_newFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, ORANGE, 25);
        handler.apply(transaction);
        assertEquals(25, Storage.getFruitQuantity(ORANGE));
    }

    @Test
    void apply_existingFruit_ok() {
        Storage.setFruitQuantity(ORANGE, 15);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, ORANGE, 25);
        handler.apply(transaction);
        assertEquals(40, Storage.getFruitQuantity(ORANGE));
    }
}

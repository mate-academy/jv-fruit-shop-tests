package core.basesyntax.operations.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.operations.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler handler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        handler = new ReturnOperationHandler();
        storage = new Storage();
    }

    @Test
    void execute_ok() {
        Storage.addFruits("apple", 3);
        FruitTransaction fruitTransaction =
                new FruitTransaction("apple", 1, Operation.RETURN);

        handler.execute(fruitTransaction);
        Assertions.assertEquals(4, Storage.getFruitBalance("apple"));
    }

    @Test
    void execute_notOk() {
        Storage.addFruits("apple", 3);
        FruitTransaction fruitTransaction =
                new FruitTransaction("apple", 1, Operation.PURCHASE);

        handler.execute(fruitTransaction);
        Assertions.assertNotEquals(3, Storage.getFruitBalance("apple"));
    }
}

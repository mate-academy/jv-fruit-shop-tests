package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitStorage storage;
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        returnOperation = new ReturnOperation(storage);
    }

    @Test
    void apply_existingQuantity_ok() {
        storage.setQuantity("apple", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10
        );
        returnOperation.apply(transaction);
        int actual = storage.getQuantity("apple");
        assertEquals(110, actual);
    }

    @Test
    void apply_emptyStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10
        );
        returnOperation.apply(transaction);
        int actual = storage.getQuantity("apple");
        assertEquals(10, actual);
    }
}

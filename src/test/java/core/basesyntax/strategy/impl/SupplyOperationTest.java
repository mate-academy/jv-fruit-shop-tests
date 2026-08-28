package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitStorage storage;
    private OperationHandler supplyOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        supplyOperation = new SupplyOperation(storage);
    }

    @Test
    void apply_emptyStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                100
        );
        supplyOperation.apply(transaction);
        int actual = storage.getQuantity("banana");
        assertEquals(100, actual);
    }

    @Test
    void apply_existingQuantity_ok() {
        storage.setQuantity("banana", 20);

        FruitTransaction transaction = new FruitTransaction(
                 FruitTransaction.Operation.SUPPLY,
                 "banana",
                 100
         );
        supplyOperation.apply(transaction);
        int actual = storage.getQuantity("banana");
        assertEquals(120, actual);
    }

}

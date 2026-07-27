package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        Storage.getFruits().clear();
    }

    @Test
    void apply_ok() {
        supplyOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                10));

        supplyOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                5));

        assertEquals(15, Storage.getFruits().get("banana"));
    }
}
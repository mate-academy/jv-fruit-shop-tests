package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        operationHandler = new SupplyOperation();
    }

    @Test
    void apply_existingFruit_addsQuantity() {
        Storage.getStorage().put("banana", 20);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                100
        );

        operationHandler.apply(transaction);

        assertEquals(120, Storage.getStorage().get("banana"));
    }

    @Test
    void apply_newFruit_addsQuantityFromZero() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                50
        );

        operationHandler.apply(transaction);

        assertEquals(50, Storage.getStorage().get("apple"));
        assertEquals(1, Storage.getStorage().size());
    }
}

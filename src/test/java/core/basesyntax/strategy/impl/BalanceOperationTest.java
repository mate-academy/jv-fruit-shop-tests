package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        operationHandler = new BalanceOperation();
    }

    @Test
    void apply_validTransaction_setsBalance() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20
        );

        operationHandler.apply(transaction);

        assertEquals(1, Storage.getStorage().size());
        assertEquals(20, Storage.getStorage().get("banana"));
    }

    @Test
    void apply_existingFruit_replacesBalance() {
        Storage.getStorage().put("banana", 50);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20
        );

        operationHandler.apply(transaction);

        assertEquals(20, Storage.getStorage().get("banana"));
    }
}

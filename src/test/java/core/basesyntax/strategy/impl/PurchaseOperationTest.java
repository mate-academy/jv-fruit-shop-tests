package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        operationHandler = new PurchaseOperation();
    }

    @Test
    void apply_enoughFruit_subtractsQuantity() {
        Storage.getStorage().put("banana", 100);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                30
        );

        operationHandler.apply(transaction);

        assertEquals(70, Storage.getStorage().get("banana"));
    }

    @Test
    void apply_notEnoughFruit_throwsException() {
        Storage.getStorage().put("banana", 20);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                30
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.apply(transaction)
        );

        assertEquals(
                "Not enough banana in storage",
                exception.getMessage()
        );

        assertEquals(20, Storage.getStorage().get("banana"));
    }
}

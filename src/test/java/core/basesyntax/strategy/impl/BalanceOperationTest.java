package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperation();
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void handle_validTransaction_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        handler.handle(transaction);

        assertEquals(20, Storage.getFruits().get("banana"));
    }

    @Test
    void handle_existingFruit_overwritesQuantity_ok() {
        Storage.getFruits().put("banana", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        handler.handle(transaction);
        assertEquals(20, Storage.getFruits().get("banana"));
    }
}

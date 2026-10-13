package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SupplyOperation();
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void handle_existingFruit_ok() {
        Storage.getFruits().put("banana", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);

        handler.handle(transaction);

        assertEquals(70, Storage.getFruits().get("banana"));
    }

    @Test
    void handle_newFruit_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);

        handler.handle(transaction);

        assertEquals(20, Storage.getFruits().get("banana"));
    }
}

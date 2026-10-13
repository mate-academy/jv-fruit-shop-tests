package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PurchaseOperation();
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void handle_enoughStock_ok() {
        Storage.getFruits().put("banana", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        handler.handle(transaction);
        assertEquals(30, Storage.getFruits().get("banana"));
    }

    @Test
    void handle_exactStock_ok() {
        Storage.getFruits().put("banana", 20);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        handler.handle(transaction);
        assertEquals(0, Storage.getFruits().get("banana"));
    }

    @Test
    void handle_notEnoughStock_notOk() {
        Storage.getFruits().put("banana", 10);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        assertThrows(RuntimeException.class, () -> handler.handle(transaction));
    }
}

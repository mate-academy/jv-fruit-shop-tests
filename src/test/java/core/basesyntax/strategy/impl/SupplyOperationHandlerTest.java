package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;
    private Storage storage;

    @BeforeAll
    public static void setUp() {
        SupplyOperationHandler operationHandler = new SupplyOperationHandler();
        Storage storage = new Storage();
    }

    @Test
    public void handle_validTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 3);
        storage.put("banana", 5);
        int expectedQuantity = 5;
        int actualQuantity = storage.get("banana");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void handle_negativeQuantity_exceptionThrown_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", -2);
        storage.put("banana", 5);
        assertThrows(RuntimeException.class, () -> supplyOperationHandler.handle(transaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUIT_STORAGE.clear();
    }
}

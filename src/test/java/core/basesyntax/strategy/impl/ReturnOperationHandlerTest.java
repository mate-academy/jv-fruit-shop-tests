package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;
    private Storage storage;

    @BeforeAll
    public static void setUp() {
        ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler();
        Storage storage = new Storage();
    }

    @Test
    public void handle_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 3);
        storage.put("banana", 2);
        int expectedQuantity = 2;
        int actualQuantity = storage.get("banana");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void returnOperation_NegativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> returnOperationHandler.handle(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", -10)));
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUIT_STORAGE.clear();
    }
}

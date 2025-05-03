package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static ReturnHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandler();
    }

    @Test
    public void handle_inputValidTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana",
                10);
        returnHandler.handle(transaction);
        int actualQuantity = Storage.get(transaction.getFruit());
        int expectedQuantity = 10;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void handle_inputZeroQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana",
                0);
        assertThrows(RuntimeException.class,
                () -> returnHandler.handle(transaction));
    }

    @Test
    public void handle_negativeInputValue_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana",
                -10);
        assertThrows(RuntimeException.class,
                () -> returnHandler.handle(transaction));
    }

    @Test
    public void handle_returnExistingFruit_ok() {
        Storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana",
                10);
        returnHandler.handle(transaction);
        int actualQuantity = Storage.get(transaction.getFruit());
        int expectedQuantity = 20;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}

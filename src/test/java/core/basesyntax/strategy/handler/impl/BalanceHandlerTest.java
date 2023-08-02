package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    public void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @AfterEach
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void handle_ValidTransaction_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 10);
        balanceHandler.handle(fruitTransaction);
        assertEquals(10, Storage.storage.get("apple"));
    }

    @Test
    public void handle_InvalidTransaction_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(null, null, 5);
        assertThrows(NullPointerException.class, ()
                -> balanceHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_NegativeQuantity_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", -20);
        assertThrows(IllegalArgumentException.class, ()
                -> balanceHandler.handle(fruitTransaction));
    }
}

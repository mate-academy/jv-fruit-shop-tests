package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private static final String VALID_FRUIT_NAME = "apple";
    private static final int INITIAL_BALANCE = 10;
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
                        VALID_FRUIT_NAME, INITIAL_BALANCE);
        balanceHandler.handle(fruitTransaction);
        assertEquals(INITIAL_BALANCE, Storage.storage.get(VALID_FRUIT_NAME));
    }

    @Test
    public void handle_InvalidTransaction_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(null, null, 5);
        NullPointerException exception =
                assertThrows(NullPointerException.class, ()
                        -> balanceHandler.handle(fruitTransaction));
        assertEquals("Invalid transaction: fruit or operation is null",
                exception.getMessage());
    }

    @Test
    public void handle_NegativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", -20);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> balanceHandler.handle(fruitTransaction));
        assertEquals("Invalid transaction: negative quantity",
                exception.getMessage());
    }
}

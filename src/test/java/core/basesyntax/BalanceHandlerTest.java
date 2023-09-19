package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BalanceHandlerTest {
    private static final String VALID_FRUIT_NAME = "dragonFruit";
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
    public void handle_NegativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, VALID_FRUIT_NAME, -10);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> balanceHandler.handle(fruitTransaction));
        assertEquals("Invalid transaction: negative quantity",
                exception.getMessage());
    }

    @Test
    public void handle_InvalidTransaction_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(null, null, INITIAL_BALANCE);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> balanceHandler.handle(fruitTransaction));
        assertEquals("Invalid transaction, something is null!",
                exception.getMessage());
    }

    @Test
    public void handle_ValidTransaction_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, VALID_FRUIT_NAME, INITIAL_BALANCE);
        balanceHandler.handle(fruitTransaction);
        assertEquals(INITIAL_BALANCE, Storage.storage.get(VALID_FRUIT_NAME));
    }
}

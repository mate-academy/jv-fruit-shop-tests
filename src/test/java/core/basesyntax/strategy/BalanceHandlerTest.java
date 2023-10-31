package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    public void setUp() {
        Storage.storage.clear();
        balanceHandler = new BalanceHandler();
    }

    @Test
    public void handle_NegativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, "dragonFruit", -10);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> balanceHandler.handle(fruitTransaction));
        assertEquals("Invalid transaction: negative quantity",
                exception.getMessage());
    }

    @Test
    public void handle_InvalidTransaction_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(null, null, 10);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> balanceHandler.handle(fruitTransaction));
        assertEquals("Invalid transaction, something is null!",
                exception.getMessage());
    }

    @Test
    public void handle_ValidTransaction_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "dragonFruit", 10);
        balanceHandler.handle(fruitTransaction);
        assertEquals(10, Storage.storage.get("dragonFruit"));
    }
}

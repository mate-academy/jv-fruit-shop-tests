package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    @Test
    public void handle_ValidTransaction_AddsToStorage_Ok() {
        BalanceOperationHandler balanceHandler = new BalanceOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 10);

        balanceHandler.handle(transaction);

        assertEquals(10, FruitStorage.FRUITS.get("Apple"));
    }

    @Test
    public void handle_NegativeBalance_ThrowsException() {
        BalanceOperationHandler balanceHandler = new BalanceOperationHandler();
        FruitStorage.FRUITS.put("Apple", 10);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", -5);

        NegativeBalanceException exception = assertThrows(NegativeBalanceException.class, () -> {
            balanceHandler.handle(transaction);
        });

        assertEquals("Negative balance is not allowed.", exception.getMessage());

        assertEquals(10, FruitStorage.FRUITS.get("Apple"));
    }
}

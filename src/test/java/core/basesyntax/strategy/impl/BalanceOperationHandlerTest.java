package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {

    @Test
    public void handle_ValidTransaction_AddsToStorage_Ok() {
        BalanceOperationHandler balanceHandler = new BalanceOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 10);

        balanceHandler.handle(transaction);

        assertEquals(10, FruitStorage.FRUITS.get("Apple"));
    }
}

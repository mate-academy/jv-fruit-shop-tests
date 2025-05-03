package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private Storage storage = new Storage();
    private BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler(storage);
    private Fruit apple = new Fruit("apple");

    @Test
    public void handleBalanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation
                .BALANCE, apple, 10);
        balanceOperationHandler.handleOperation(transaction);

        int quantity = storage.getFruitQuantity(apple);
        assertEquals(10, quantity);
    }

    @Test
    public void failedToHandleBalanceOperationWithNegativeQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation
                .BALANCE, apple, -10);

        assertThrows(RuntimeException.class, () -> balanceOperationHandler
                .handleOperation(transaction));
    }
}

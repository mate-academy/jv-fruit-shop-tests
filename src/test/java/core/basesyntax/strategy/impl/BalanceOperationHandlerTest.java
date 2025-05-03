package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationHandler();
        FruitStorage.fruits.put("orange", 70);
    }

    @Test
    public void balanceHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "orange", 70);
        operationHandler.handle(transaction);
        int expected = 70;
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}

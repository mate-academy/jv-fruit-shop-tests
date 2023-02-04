package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationHandler();
        FruitStorage.fruits.put("banana", 20);
    }

    @Test
    public void balanceHandler_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        operationHandler.handle(transaction);
        int expected = 20;
        int actual = FruitStorage.fruits.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruits.clear();
    }
}

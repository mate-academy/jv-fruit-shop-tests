package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    private FruitTransaction f1 =
            new FruitTransaction(Operation.BALANCE, "pineapple", 300);
    private FruitTransaction f2 =
            new FruitTransaction(Operation.BALANCE, "strawberry", 50);
    private BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Before
    public void cleanBefore() {
        Storage.fruits.clear();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void balanceOperationHandler_process_Ok() {
        balanceOperationHandler.process(f1);
        balanceOperationHandler.process(f2);

        Assert.assertEquals(Storage.fruits.size(),2);
        Assert.assertEquals(Storage.fruits.get("pineapple"),(Integer) 300);
        Assert.assertEquals(Storage.fruits.get("strawberry"), (Integer) 50);
    }
}

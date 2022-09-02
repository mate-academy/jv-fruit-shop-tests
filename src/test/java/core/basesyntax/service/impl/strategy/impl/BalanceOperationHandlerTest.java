package core.basesyntax.service.impl.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperationHandler_validFruitTransaction_Ok() {
        Transaction transaction = new Transaction("b", new Fruit("banana"), 20);
        operationHandler.apply(transaction);
        Integer expected = 20;
        Integer actual = Storage.getStorage().get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperationHandler_nullFruitTransaction_NotOk() {
        operationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}

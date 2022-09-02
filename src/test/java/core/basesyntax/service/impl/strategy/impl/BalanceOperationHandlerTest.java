package core.basesyntax.service.impl.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperationHandler_validFruitTransaction_Ok() {
        Transaction transaction = new Transaction("b", new Fruit("banana"), 20);
        balanceOperationHandler.apply(transaction);
        Integer expected = 20;
        Integer actual = Storage.getStorage().get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperationHandler_NullFruitTransaction_NotOk() {
        balanceOperationHandler.apply(null);
    }

    @Test
    public void balanceOperationHandler_FruitTransactionPresentInStorage() {
        Transaction transaction = new Transaction("b", new Fruit("banana"), 20);
        Storage.getStorage().put(new Fruit("banana"), 20);
        Integer expected = 40;
        balanceOperationHandler.apply(transaction);
        Integer actual = Storage.getStorage().get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}

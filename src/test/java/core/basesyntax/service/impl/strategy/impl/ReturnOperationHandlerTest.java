package core.basesyntax.service.impl.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void purchaseOperationHandler_validFruitTransaction_Ok() {
        Storage.getStorage().put(new Fruit("apple"), 20);
        Transaction transaction = new Transaction("r", new Fruit("apple"), 10);
        returnOperationHandler.apply(transaction);
        Integer expected = 30;
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandler_NullFruitTransaction_NotOk() {
        returnOperationHandler.apply(null);
    }

    @Test
    public void purchaseOperationHandler_FruitTransactionPresentInStorage() {
        Transaction transaction = new Transaction("r", new Fruit("apple"), 20);
        Storage.getStorage().put(new Fruit("apple"), 10);
        Integer expected = 30;
        returnOperationHandler.apply(transaction);
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}

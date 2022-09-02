package core.basesyntax.service.impl.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseOperationHandler_validFruitTransaction_Ok() {
        Storage.getStorage().put(new Fruit("apple"), 20);
        Transaction transaction = new Transaction("p", new Fruit("apple"), 10);
        purchaseOperationHandler.apply(transaction);
        Integer expected = 10;
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandler_NullFruitTransaction_NotOk() {
        purchaseOperationHandler.apply(null);
    }

    @Test
    public void purchaseOperationHandler_FruitTransactionPresentInStorage() {
        Transaction transaction = new Transaction("p", new Fruit("apple"), 20);
        Storage.getStorage().put(new Fruit("apple"), 10);
        Integer expected = 10;
        purchaseOperationHandler.apply(transaction);
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }

}

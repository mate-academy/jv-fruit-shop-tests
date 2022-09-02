package core.basesyntax.service.impl.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void supplyOperationHandler_validFruitTransaction_Ok() {
        Storage.getStorage().put(new Fruit("apple"), 20);
        Transaction transaction = new Transaction("s", new Fruit("apple"), 10);
        supplyOperationHandler.apply(transaction);
        Integer expected = 30;
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperationHandler_NullFruitTransaction_NotOk() {
        supplyOperationHandler.apply(null);
    }

    @Test
    public void supplyOperationHandler_FruitTransactionPresentInStorage() {
        Transaction transaction = new Transaction("s", new Fruit("apple"), 20);
        Storage.getStorage().put(new Fruit("apple"), 10);
        Integer expected = 30;
        supplyOperationHandler.apply(transaction);
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }

}

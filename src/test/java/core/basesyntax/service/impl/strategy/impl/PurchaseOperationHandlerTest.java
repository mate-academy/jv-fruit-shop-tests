package core.basesyntax.service.impl.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseOperationHandler_validFruitTransaction_Ok() {
        Storage.getStorage().put(new Fruit("apple"), 25);
        Transaction transaction = new Transaction("p", new Fruit("apple"), 15);
        operationHandler.apply(transaction);
        Integer expected = 10;
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandler_nullFruitTransaction_NotOk() {
        operationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }

}

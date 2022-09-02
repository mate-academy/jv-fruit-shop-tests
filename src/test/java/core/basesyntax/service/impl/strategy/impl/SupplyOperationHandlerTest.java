package core.basesyntax.service.impl.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void supplyOperationHandler_validFruitTransaction_Ok() {
        Storage.getStorage().put(new Fruit("apple"), 20);
        Transaction transaction = new Transaction("s", new Fruit("apple"), 10);
        operationHandler.apply(transaction);
        Integer expected = 30;
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperationHandler_nullFruitTransaction_NotOk() {
        operationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }

}

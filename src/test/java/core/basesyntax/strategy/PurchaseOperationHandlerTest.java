package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void operatePurchase_check_ok() {
        Fruit fruit = new Fruit("testFruit");
        Storage.storage.put(fruit, 10);
        operationHandler.apply(new FruitTransaction("p", new Fruit("testFruit"), 10));
        Integer expected = 0;
        Integer actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operatePurchase_notCheckDate_notOk() {
        Fruit fruit = new Fruit("testFruit");
        Storage.storage.put(new Fruit("testFruit"), 0);
        operationHandler.apply(new FruitTransaction("p", fruit, 50));
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}

package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
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

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void handlePurchaseOperation_enoughFruits_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 30);
        operationHandler.process(banana, 30);
        int actual = Storage.storage.get(banana);
        int expected = 0;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handlePurchaseOperation_notEnoughFruits_notOk() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 7);
        operationHandler.process(apple, 12);
    }
}

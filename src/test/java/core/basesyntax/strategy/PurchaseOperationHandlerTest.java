package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransfer;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void process_ok() {
        Storage.fruits.put(new Fruit("banana"), 100);
        FruitTransfer fruit = new FruitTransfer(FruitTransfer.Operation.PURCHASE,
                new Fruit("banana"), 100);
        purchaseOperationHandler.process(fruit);
        Integer expected = 0;
        Assert.assertEquals(expected, Storage.fruits.get(fruit.getFruit()));
    }

    @Test(expected = RuntimeException.class)
    public void process_notOk() {
        Storage.fruits.put(new Fruit("banana"), 100);
        FruitTransfer fruit = new FruitTransfer(FruitTransfer.Operation.PURCHASE,
                new Fruit("banana"), 101);
        purchaseOperationHandler.process(fruit);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}

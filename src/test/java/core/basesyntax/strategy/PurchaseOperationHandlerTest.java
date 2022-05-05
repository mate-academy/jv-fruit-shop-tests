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
    private static Fruit fruit;
    private static FruitTransfer fruitTransfer;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruit = new Fruit("banana");
    }

    @Test
    public void process_ok() {
        Storage.fruits.put(fruit, 100);
        fruitTransfer = new FruitTransfer(FruitTransfer.Operation.PURCHASE, fruit, 100);
        purchaseOperationHandler.process(fruitTransfer);
        Integer expected = 0;
        Integer actual = Storage.fruits.get(fruitTransfer.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void process_notOk() {
        Storage.fruits.put(fruit, 100);
        fruitTransfer = new FruitTransfer(FruitTransfer.Operation.PURCHASE,
                fruit, 101);
        purchaseOperationHandler.process(fruitTransfer);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}

package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseFruitOperationHandlerTest {
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        fruitOperationHandler = new PurchaseFruitOperationHandler();
    }

    @Test
    public void operatePurchaseHandlerOk() {
        Storage.fruitStorage.put("passionFruit", 30);
        Fruit fruit = new Fruit("p", "passionFruit", 28);
        fruitOperationHandler.operate(fruit);
        Integer expected = 2;
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operatePurchaseHandlerNotOk() {
        Fruit fruit = new Fruit("p", "passionFruit", 28);
        fruitOperationHandler.operate(fruit);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}

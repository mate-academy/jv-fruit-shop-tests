package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseFruitOperationTest {
    private static FruitOperation fruitOperation;

    @BeforeClass
    public static void setUp() {
        fruitOperation = new PurchaseFruitOperation();
    }

    @Test
    public void operatePurchaseAdd_valid_ok() {
        Fruit fruit = new Fruit("p", "passionFruit", 20);
        Storage.fruitStorage.put("passionFruit", 20);
        fruitOperation.operate(fruit);
        Integer expected = 0;
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operatePurchaseAdd_notValidDate_notOk() {
        Fruit fruit = new Fruit("p", "passionFruit", 20);
        Storage.fruitStorage.put("passionFruit", 0);
        fruitOperation.operate(fruit);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}

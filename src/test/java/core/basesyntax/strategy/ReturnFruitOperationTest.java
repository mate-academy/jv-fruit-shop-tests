package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnFruitOperationTest {
    private static FruitOperation fruitOperation;

    @BeforeClass
    public static void setUp() {
        fruitOperation = new ReturnFruitOperation();
    }

    @Test
    public void operateReturnAddIsOk() {
        Fruit fruit = new Fruit("b", "passionFruit", 20);
        Storage.fruitStorage.put("passionFruit", 20);
        fruitOperation.operate(fruit);
        Integer expected = 40;
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}

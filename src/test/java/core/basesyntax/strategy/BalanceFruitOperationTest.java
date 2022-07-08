package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceFruitOperationTest {
    private static FruitOperation fruitOperation;

    @BeforeClass
    public static void setUp() {
        fruitOperation = new BalanceFruitOperation();
    }

    @Test
    public void operateBalanceAdd_validDate_ok() {
        Fruit fruit = new Fruit("b", "passionFruit", 20);
        fruitOperation.operate(fruit);
        Integer expected = 20;
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}

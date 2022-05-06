package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceFruitOperationHandlerTest {
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        fruitOperationHandler = new BalanceFruitOperationHandler();
    }

    @Test
    public void operateBalanceHandlerIsOk() {
        Fruit fruit = new Fruit("b", "passionFruit", 28);
        fruitOperationHandler.operate(fruit);
        Integer expected = fruit.getQuantity();
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operateBalanceHandlerAddIsOk() {
        Fruit fruit = new Fruit("b", "passionFruit", 20);
        Storage.fruitStorage.put("passionFruit", 20);
        fruitOperationHandler.operate(fruit);
        Integer expected = 40;
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}

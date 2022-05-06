package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyFruitOperationHandlerTest {
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        fruitOperationHandler = new SupplyFruitOperationHandler();
    }

    @Test
    public void operateSupplyHandlerFirstFruitIsOk() {
        Fruit fruit = new Fruit("s", "passionFruit", 10);
        fruitOperationHandler.operate(fruit);
        Integer expected = fruit.getQuantity();
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operateSupplyHandlerAddSecondFruitIsOk() {
        Fruit fruit = new Fruit("b", "passionFruit", 20);
        Storage.fruitStorage.put("passionFruit", 20);
        fruitOperationHandler.operate(fruit);
        Integer expected = 40;
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruitStorage.clear();
    }
}

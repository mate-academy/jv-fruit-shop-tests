package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
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
    public void operateSupplyHandlerIsOk() {
        Fruit fruit = new Fruit("s", "passionFruit", 10);
        fruitOperationHandler.operate(fruit);
        Integer expected = fruit.getQuantity();
        Integer actual = Storage.fruitStorage.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}

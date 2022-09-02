package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void operateReturn_checkDate_ok() {
        Fruit fruit = new Fruit("testFruit");
        Storage.storage.put(fruit, 20);
        operationHandler.apply(new FruitTransaction("s", new Fruit("testFruit"), 5));
        Integer expected = 25;
        Integer actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void tearDown() {
        Storage.storage.clear();
    }
}

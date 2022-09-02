package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
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

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}

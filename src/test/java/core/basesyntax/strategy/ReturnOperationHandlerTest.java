package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnOperationHandler();
    }


    @Test
    public void handleReturnOperationEmptyStorage_ok() {
        Fruit banana = new Fruit("banana");
        operationHandler.process(banana, 23);
        int actual = Storage.storage.get(banana);
        int expected = 23;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handleReturnOperationExistingStorage_ok() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 33);
        operationHandler.process(apple, 37);
        int actual = Storage.storage.get(apple);
        int expected = 70;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

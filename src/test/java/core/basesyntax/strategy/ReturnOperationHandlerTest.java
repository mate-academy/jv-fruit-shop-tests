package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
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

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void handleReturnOperation_emptyStorage_ok() {
        Fruit banana = new Fruit("banana");
        operationHandler.process(banana, 23);
        Map<Fruit, Integer> actual = Storage.storage;
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(banana, 23);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handleReturnOperation_existingStorage_ok() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 33);
        operationHandler.process(apple, 37);
        Map<Fruit, Integer> actual = Storage.storage;
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(apple, 70);
        Assert.assertEquals(expected, actual);
    }
}

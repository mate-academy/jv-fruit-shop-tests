package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void apply_validReturnOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        operationHandler.apply(new Transaction("r", new Fruit("banana"), 10));
        Integer expected = 20;
        Integer actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruitReturnOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        operationHandler.apply(new Transaction("r", null, 10));
        Integer expected = 20;
        Integer actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullQuantityReturnOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        operationHandler.apply(new Transaction("r", new Fruit("banana"), null));
        Integer expected = 20;
        Integer actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

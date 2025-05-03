package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void apply_validSupplyOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        operationHandler.apply(new Transaction("s", new Fruit("banana"), 5));
        Integer expected = 15;
        Integer actual = Storage.storage.get(banana);
        assertEquals("Invalid supply operation ", expected, actual);
    }

    @Test
    public void apply_validSupplyOperationMoreThanOneFruit_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        operationHandler.apply(new Transaction("s", new Fruit("banana"), 5));
        operationHandler.apply(new Transaction("s", new Fruit("banana"), 15));
        Integer expected = 30;
        Integer actual = Storage.storage.get(banana);
        assertEquals("Invalid supply operation ", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruitSupplyOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        operationHandler.apply(new Transaction("s", null, 5));
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullQuantitySupplyOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        operationHandler.apply(new Transaction("s", new Fruit("banana"), null));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

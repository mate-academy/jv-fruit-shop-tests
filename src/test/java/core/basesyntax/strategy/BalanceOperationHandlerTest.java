package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void apply_validBalanceOperation_Ok() {
        Fruit banana = new Fruit("banana");
        operationHandler.apply(new Transaction("b", banana, 30));
        Integer expected = 30;
        Integer actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_validBalanceOperationMoreThanOneFruit_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 25);
        operationHandler.apply(new Transaction("b", new Fruit("banana"), 15));
        Integer expected = 40;
        Integer actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullQuantityBalanceOperation_NotOk() {
        Fruit banana = new Fruit("banana");
        operationHandler.apply(new Transaction("b", new Fruit("banana"), null));
        Integer expected = 15;
        Integer actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

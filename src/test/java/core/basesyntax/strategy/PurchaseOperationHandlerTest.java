package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void apply_validPurchaseOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 30);
        operationHandler.apply(new Transaction("p", new Fruit("banana"), 15));
        Integer expected = 15;
        Integer actual = Storage.storage.get(banana);
        assertEquals("Invalid purchase operation ", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruitPurchaseOperation_NotOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 30);
        operationHandler.apply(new Transaction("p", null, 15));
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullQuantityPurchaseOperation_NotOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 30);
        operationHandler.apply(new Transaction("p", new Fruit("banana"), null));
    }

    @Test
    public void apply_higherQuantityPurchaseOperation_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 30);
        operationHandler.apply(new Transaction("p", new Fruit("banana"), 50));
        Integer expected = 30;
        Integer actual = Storage.storage.get(banana);
        assertEquals("Valid purchase operation with ", expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchase_OneFruit_Ok() {
        Storage.storage.put(new Fruit("apple"), 101);
        purchaseOperationHandler.process(new Fruit("apple"), 100);
        Integer actual = 1;
        assertEquals(actual, Storage.storage.get(new Fruit("apple")));
    }

    @Test
    public void purchase_ManyFruit_Ok() {
        Storage.storage.put(new Fruit("banana"), 50);
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("mango"), 150);
        purchaseOperationHandler.process(new Fruit("banana"), 10);
        purchaseOperationHandler.process(new Fruit("apple"), 20);
        purchaseOperationHandler.process(new Fruit("mango"), 50);
        assertEquals(40, (int) Storage.storage.get(new Fruit("banana")));
        assertEquals(80, (int) Storage.storage.get(new Fruit("apple")));
        assertEquals(100, (int) Storage.storage.get(new Fruit("mango")));
        assertEquals(3, Storage.storage.size());
    }

    @Test (expected = RuntimeException.class)
    public void purchase_NotOk() {
        Storage.storage.put(new Fruit("banana"), 1);
        purchaseOperationHandler.process(new Fruit("banana"), 2);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}

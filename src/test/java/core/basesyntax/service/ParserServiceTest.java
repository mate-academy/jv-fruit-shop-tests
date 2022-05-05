package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void onePurchase_Ok() {
        Storage.storage.put(new Fruit("apple"), 10);
        purchaseOperationHandler.process(new Fruit("apple"), 5);
        Integer actual = 5;
        assertEquals(actual, Storage.storage.get(new Fruit("apple")));
    }

    @Test
    public void morePurchase_Ok() {
        Storage.storage.put(new Fruit("banana"), 40);
        Storage.storage.put(new Fruit("apple"), 20);
        Storage.storage.put(new Fruit("mango"), 60);
        purchaseOperationHandler.process(new Fruit("banana"), 10);
        purchaseOperationHandler.process(new Fruit("apple"), 5);
        purchaseOperationHandler.process(new Fruit("banana"), 5);
        purchaseOperationHandler.process(new Fruit("mango"), 45);
        purchaseOperationHandler.process(new Fruit("mango"), 15);
        assertEquals(25, (int) Storage.storage.get(new Fruit("banana")));
        assertEquals(15, (int) Storage.storage.get(new Fruit("apple")));
        assertEquals(0, (int) Storage.storage.get(new Fruit("mango")));
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

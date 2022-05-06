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
    public void purchase_oneFruit_Ok() {
        Storage.storage.put(new Fruit("apple"), 101);
        purchaseOperationHandler.process(new Fruit("apple"), 100);
        int expected = 1;
        int actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void purchase_manyFruit_Ok() {
        Storage.storage.put(new Fruit("banana"), 50);
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("mango"), 150);
        purchaseOperationHandler.process(new Fruit("banana"), 10);
        purchaseOperationHandler.process(new Fruit("apple"), 20);
        purchaseOperationHandler.process(new Fruit("mango"), 50);
        int expectedBanana = 40;
        int actualBanana = Storage.storage.get(new Fruit("banana"));
        assertEquals(expectedBanana, actualBanana);

        int expectedApple = 80;
        int actualApple = Storage.storage.get(new Fruit("apple"));
        assertEquals(expectedApple, actualApple);

        int expectedMango = 100;
        int actualMango = Storage.storage.get(new Fruit("mango"));
        assertEquals(expectedMango, actualMango);

        int expectedSize = 3;
        int actualSize = Storage.storage.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test (expected = RuntimeException.class)
    public void purchase_oneFruit_notOk() {
        Storage.storage.put(new Fruit("banana"), 1);
        purchaseOperationHandler.process(new Fruit("banana"), 2);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}

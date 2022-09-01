package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchase;

    @BeforeClass
    public static void setUp() {
        purchase = new PurchaseOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void purchaseFruits_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        purchase.apply(new Transaction("b", banana, 10));
        assertEquals("Expected another value 0",
                Integer.valueOf(0),
                Storage.storage.get(banana));
    }

    @Test (expected = RuntimeException.class)
    public void purchaseFruitsThatNotInStore_notOk() {
        purchase.apply(new Transaction("b", new Fruit("banana"), 10));
    }

    @Test (expected = RuntimeException.class)
    public void purchaseFruitsThatNotEnoughInStore_notOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 5);
        purchase.apply(new Transaction("b", banana, 10));
    }

    @AfterClass
    public static void clean() {
        Storage.storage.clear();
    }
}

package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
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
    public void operationPurchase_fruits_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        purchase.apply(new Transaction("b", banana, 10));
        assertEquals("Expected another value 0",
                Integer.valueOf(0),
                Storage.storage.get(banana));
    }

    @Test (expected = RuntimeException.class)
    public void operationPurchase_fruitsThatNotInStore_notOk() {
        purchase.apply(new Transaction("b", new Fruit("banana"), 10));
    }

    @Test (expected = RuntimeException.class)
    public void operationPurchase_fruitsThatNotEnoughInStore_notOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 5);
        purchase.apply(new Transaction("b", banana, 10));
    }

    @Test (expected = RuntimeException.class)
    public void operationPurchase_nullAmount_notOk() {
        purchase.apply(new Transaction("b", new Fruit("banana"), null));
    }

    @Test (expected = NullPointerException.class)
    public void operationPurchase_nullTransaction_notOk() {
        purchase.apply(null);
    }
}

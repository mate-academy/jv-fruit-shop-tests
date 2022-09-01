package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
    private Fruit fruit;

    @Before
    public void beforeClass() throws Exception {
        fruit = new Fruit("apple");
    }

    @Test
    public void applyPurchase_OK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        purchaseOperationHandler.apply(new Transaction("p", new Fruit("apple"), 9));
        Integer expected = 5;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void negativePurchaseValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        purchaseOperationHandler.apply(new Transaction("p", new Fruit("apple"), -9));
        Integer expected = Integer.valueOf(5);
        Integer actual = Storage.storage.get(fruit);
        assertFalse(expected.equals(actual));
    }

    @Test (expected = NullPointerException.class)
    public void nullPurchaseValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        purchaseOperationHandler.apply(new Transaction("p", new Fruit("apple"), null));
    }
}

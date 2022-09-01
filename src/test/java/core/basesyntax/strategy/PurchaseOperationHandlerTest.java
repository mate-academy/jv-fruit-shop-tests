package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;


public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
    private Transaction inputTransaction;
    private Fruit fruit;

    @Before
    public void beforeClass() throws Exception {
        inputTransaction = new Transaction("p", new Fruit("apple"), 9);
        fruit = inputTransaction.getFruit();
    }

    @Test
    public void applyPurchase_OK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        purchaseOperationHandler.apply(new Transaction("p", new Fruit("apple"), 9));
        Integer expected = Integer.valueOf(5);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        purchaseOperationHandler.apply(new Transaction("p", new Fruit("apple"), -9));
        Integer expected = Integer.valueOf(5);
        Integer actual = Storage.storage.get(fruit);
        assertFalse(expected.equals(actual));
    }
}
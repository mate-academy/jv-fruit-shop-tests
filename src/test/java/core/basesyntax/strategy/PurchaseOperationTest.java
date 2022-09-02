package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.operations.PurchaseOperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private OperationHandler purchaseOperationTest;

    @Before
    public void setUp() {
        purchaseOperationTest = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseTestQuantity_Ok() {
        Fruit apple = new Fruit("AdamDontEatIt");
        Storage.storage.put(apple, 30);
        purchaseOperationTest.apply(new Transaction("p", apple, 9));
        Integer expected = 21;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeTestPurchaseValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 1000);
        purchaseOperationTest.apply(new Transaction("p", new Fruit("apple"), -999));
        Integer expected = 1;
        Integer actual = Storage.storage.get(apple);
        assertNotEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void purchaseGetEmptyValue_notOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 99);
        purchaseOperationTest.apply(new Transaction("p", banana, null));
    }

    @After
    public void clearStorage() {
        Storage.storage.clear();
    }
}

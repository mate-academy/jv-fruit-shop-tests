package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void beforeClass() throws Exception {
        operationHandler = new PurchaseOperationHandler();
        apple = new Fruit("apple");
    }

    @Test
    public void applyPurchase_OK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("p", apple, 9));
        Integer expected = 5;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void negativePurchaseValue_NotOK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("p", apple, -9));
        Integer expected = Integer.valueOf(5);
        Integer actual = Storage.storage.get(this.apple);
        assertFalse(expected.equals(actual));
    }

    @Test (expected = NullPointerException.class)
    public void nullPurchaseValue_NotOK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("p", apple, null));
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}

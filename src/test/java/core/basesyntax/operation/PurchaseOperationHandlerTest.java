package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void apply_validFruitTransaction_Ok() {
        Storage.storage.put(new Fruit("banana"), 5);
        FruitTransaction fruitTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        purchaseOperationHandler.apply(fruitTransaction);
        Integer expected = -5;
        Integer actual = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitTransaction_NotOk() {
        purchaseOperationHandler.apply(null);
    }

    @Test
    public void apply_FruitTransactionIsPresentInStorage_Ok() {
        FruitTransaction bananaTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("banana"), 5);
        Integer expected = -5;
        purchaseOperationHandler.apply(bananaTransaction);
        Integer actual = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}

package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler purchaseOperation;
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        purchaseOperation = new PurchaseOperationHandler();
        fruit = new Fruit("banana");
        Storage.storage.put(fruit, 100);
    }

    @Test
    public void apply_Ok() {
        purchaseOperation.apply(new FruitTransaction("p", fruit, 15));
        Integer expected = 85;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullValue_NotOk() {
        purchaseOperation.apply(new FruitTransaction("p", fruit, null));
    }

    @Test(expected = NullPointerException.class)
    public void apply_Null_NotOk() {
        purchaseOperation.apply(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}

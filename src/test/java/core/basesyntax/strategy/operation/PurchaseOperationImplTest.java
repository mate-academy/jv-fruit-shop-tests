package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationImplTest {
    private static final String APPLE_FRUIT = "apple";
    private static OperationHandler purchaseOperation;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperation = new PurchaseOperationImpl();
    }

    @Test
    public void getResultBalance_resultOperation_ok() {
        Store.FRUIT_STORAGE.put(APPLE_FRUIT, 100);
        purchaseOperation.getResultBalance(APPLE_FRUIT, 50);
        int expected = 50;
        int actual = Store.FRUIT_STORAGE.get(APPLE_FRUIT);
        assertEquals("Expected quantity: " + expected + ", but was" + actual,
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getResultBalance_resultOperation_ifFruitDontExist_notOk() {
        Store.FRUIT_STORAGE.put(APPLE_FRUIT, 200);
        purchaseOperation.getResultBalance("banana", 100);
    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}

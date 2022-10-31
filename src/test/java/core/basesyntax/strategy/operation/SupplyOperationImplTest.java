package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationImplTest {
    private static final String APPLE_FRUIT = "apple";
    private static OperationHandler supplyOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyOperation = new SupplyOperationImpl();
    }

    @Test
    public void getResultBalance_supplyOperation_ok() {
        Store.FRUIT_STORAGE.put(APPLE_FRUIT, 100);
        supplyOperation.getResultBalance(APPLE_FRUIT, 50);
        int expected = 150;
        int actual = Store.FRUIT_STORAGE.get(APPLE_FRUIT);
        assertEquals("Expected quantity: " + expected + ", but was" + actual,
                expected, actual);
    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}

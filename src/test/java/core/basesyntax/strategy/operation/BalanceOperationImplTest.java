package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationImplTest {
    private static final String BANANA_FRUIT = "banana";
    private static OperationHandler balanceOperation;

    @BeforeClass
    public static void beforeClass() {
        balanceOperation = new BalanceOperationImpl();
    }

    @Test
    public void getResultBalance_balanceOperation_ifNull_Ok() {
        Store.FRUIT_STORAGE.put(BANANA_FRUIT, null);
        balanceOperation.getResultBalance(BANANA_FRUIT, 100);
        int expected = 100;
        int actual = Store.FRUIT_STORAGE.get(BANANA_FRUIT);
        assertEquals("Expected quantity: " + expected + ", but was" + actual,
                expected, actual);
    }

    @Test
    public void getResultBalance_balanceOperation_ifQuantityExist_Ok() {
        Store.FRUIT_STORAGE.put(BANANA_FRUIT, 10);
        balanceOperation.getResultBalance(BANANA_FRUIT, 200);
        int expected = 210;
        int actual = Store.FRUIT_STORAGE.get(BANANA_FRUIT);
        assertEquals("Expected quantity: " + expected + ", but was" + actual,
                expected, actual);
    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}

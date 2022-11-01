package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationImplTest {
    private static final String BANANA_FRUIT = "banana";
    private static OperationHandler returnOperation;

    @BeforeClass
    public static void beforeClass() {
        returnOperation = new ReturnOperationImpl();
    }

    @Test
    public void getResultBalance_returnOperation_ok() {
        Store.FRUIT_STORAGE.put(BANANA_FRUIT, 100);
        returnOperation.getResultBalance(BANANA_FRUIT, 100);
        int expected = 200;
        int actual = Store.FRUIT_STORAGE.get(BANANA_FRUIT);
        assertEquals("Expected quantity: " + expected + ", but was" + actual,
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getResultBalance_returnOperation_ifFruitDontExist_notOk() {
        Store.FRUIT_STORAGE.put(BANANA_FRUIT, 200);
        returnOperation.getResultBalance("apple", 100);
    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}

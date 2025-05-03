package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationImplTest {
    private static final String BANANA_FRUIT = "banana";
    private static final String APPLE_FRUIT = "apple";
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

    @Test()
    public void getResultBalance_returnOperation_ifFruitDontExist_ok() {
        Store.FRUIT_STORAGE.put(BANANA_FRUIT, 200);
        returnOperation.getResultBalance(APPLE_FRUIT, 100);
        int expectedAppleQuantity = 100;
        int actual = Store.FRUIT_STORAGE.get(APPLE_FRUIT);
        assertEquals("Expected quantity: " + expectedAppleQuantity + ", but was" + actual,
                expectedAppleQuantity, actual);

    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}

package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static Activity activity;
    private static final int NEGATIVE_COUNT_OF_STORAGE = -5;
    private static final int POSITIVE_CURRENT_COUNT = 15;
    private static final int POSITIVE_COUNT_OF_STORAGE_NOT_OK = 25;

    @BeforeClass
    public static void beforeClass() {
        activity = new PurchaseHandler();
    }

    @Test
    public void calculateCountWithTwoPositiveOperands_Ok() {
        int expected = 10;
        int actual = activity.calculateCount(POSITIVE_CURRENT_COUNT,
                BalanceHandlerTest.POSITIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculateCountWithPositiveOperands_NotOK() {
        activity.calculateCount(POSITIVE_CURRENT_COUNT,
                        POSITIVE_COUNT_OF_STORAGE_NOT_OK);
    }

    @Test
    public void calculateCountWithPositiveAndNegativeOperands_Ok() {
        int expected = 20;
        int actual = activity.calculateCount(POSITIVE_CURRENT_COUNT,
                NEGATIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculateCountWithNegativeAndPositiveOperands_NotOk() {
        activity.calculateCount(BalanceHandlerTest.NEGATIVE_CURRENT_COUNT,
                 BalanceHandlerTest.POSITIVE_COUNT_OF_STORAGE);
    }
}

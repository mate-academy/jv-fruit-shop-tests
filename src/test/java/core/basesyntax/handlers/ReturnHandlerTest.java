package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static Activity activity;

    @BeforeClass
    public static void beforeClass() {
        activity = new ReturnHandler();
    }

    @Test
    public void calculateCountWithTwoPositiveOperands_Ok() {
        int expected = 15;
        int actual = activity.calculateCount(BalanceHandlerTest.POSITIVE_CURRENT_COUNT,
                BalanceHandlerTest.POSITIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateCountWithTwoNegativeOperands_Ok() {
        int expected = -25;
        int actual = activity.calculateCount(BalanceHandlerTest.NEGATIVE_CURRENT_COUNT,
                BalanceHandlerTest.NEGATIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateCountWithPositiveAndNegativeOperands_Ok() {
        int expected = 0;
        int actual = activity.calculateCount(BalanceHandlerTest.POSITIVE_CURRENT_COUNT,
                BalanceHandlerTest.NEGATIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }
}

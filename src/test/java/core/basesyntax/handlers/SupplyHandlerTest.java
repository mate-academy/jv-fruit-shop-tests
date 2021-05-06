package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static Activity activity;
    private static final int POSITIVE_CURRENT_COUNT = 10;
    private static final int NEGATIVE_CURRENT_COUNT = -15;
    private static final int POSITIVE_COUNT_OF_STORAGE = 5;
    private static final int NEGATIVE_COUNT_OF_STORAGE = -10;

    @BeforeClass
    public static void beforeClass() {
        activity = new SupplyHandler();
    }

    @Test
    public void calculateCountWithTwoPositiveOperands_Ok() {
        int expected = 15;
        int actual = activity.calculateCount(POSITIVE_CURRENT_COUNT,
                POSITIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateCountWithTwoNegativeOperands_Ok() {
        int expected = -25;
        int actual = activity.calculateCount(NEGATIVE_CURRENT_COUNT,
                NEGATIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateCountWithPositiveAndNegativeOperands_Ok() {
        int expected = 0;
        int actual = activity.calculateCount(POSITIVE_CURRENT_COUNT,
                NEGATIVE_COUNT_OF_STORAGE);
        assertEquals(expected, actual);
    }
}

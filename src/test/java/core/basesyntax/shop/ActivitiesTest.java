package core.basesyntax.shop;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ActivitiesTest {
    private static final String ACTIVITY = "B";
    private static final String ACTIVITY_NOT_OK = "X";

    @Test
    public void checkIsValidActivities_OK() {
        boolean actual = Activities.isValid(ACTIVITY);
        assertTrue(actual);
    }

    @Test
    public void checkIsValidActivities_NotOK() {
        boolean actual = Activities.isValid(ACTIVITY_NOT_OK);
        assertFalse(actual);
    }
}

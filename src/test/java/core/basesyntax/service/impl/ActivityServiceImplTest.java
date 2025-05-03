package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Activity;
import core.basesyntax.service.ActivityService;
import org.junit.Before;
import org.junit.Test;

public class ActivityServiceImplTest {
    private static final Activity.Builder ACTIVITY_BUILDER = new Activity.Builder();
    private static final String TEST_DATA = "b,banana,10";
    private ActivityService activityService;
    private Activity expected;

    @Before
    public void setUp() {
        activityService = new ActivityServiceImpl();
        expected = ACTIVITY_BUILDER
                .setItem("banana").setQuantity(10).setOperation("b").build();
    }

    @Test
    public void parse_Ok() {
        Activity actual = activityService.parse(TEST_DATA);
        assertEquals(actual, expected);
    }
}

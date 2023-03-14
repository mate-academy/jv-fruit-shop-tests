package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Activity;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ActivityService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ActivityServiceImplTest {
    private static final String ACTIVITY_KEY = "testItem";
    private static final String ACTIVITY_Code = "b";
    private static final int ACTIVITY_VALUE = 10;
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final ActivityService activityService = new ActivityServiceImpl();
    private final Activity.Builder activityBuilder = new Activity.Builder();
    private final List<String> data = new ArrayList<>();
    private List<Activity> testActivities = new ArrayList<>();

    @Before
    public void setUp() {
        data.add("Legend");
        Activity expectedActivity = activityBuilder
                .setOperation(Operation.BALANCE)
                .setItem(ACTIVITY_KEY)
                .setQuantity(ACTIVITY_VALUE)
                .build();
        testActivities.add(expectedActivity);
    }

    @After
    public void tearDown() {
        data.clear();
    }

    @Test
    public void getActivitiesFromInput() {
        String input = "b,testItem,10";
        data.add(input);
        testActivities = activityService.getActivitiesFromInput(data);
        final String actualKey = testActivities.get(0).getItem();
        final int actualValue = testActivities.get(0).getQuantity();
        final String actualCode = testActivities.get(0).getOperation().getOperationCode();
        assertEquals(ACTIVITY_KEY, actualKey);
        assertEquals(ACTIVITY_VALUE, actualValue);
        assertEquals(ACTIVITY_Code, actualCode);
    }

    @Test
    public void getIncorrectInput_NotOk() {
        String incorrectInput = "testItem,10";
        data.add(incorrectInput);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Can't parse activity from data"));
        activityService.getActivitiesFromInput(data);
    }
}

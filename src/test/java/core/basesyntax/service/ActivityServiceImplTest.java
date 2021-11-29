package core.basesyntax.service;

import core.basesyntax.model.ActivityType;
import core.basesyntax.service.activityhandler.ActivityHandler;
import core.basesyntax.service.activityhandler.BalanceHandler;
import core.basesyntax.service.activityhandler.PurchaseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActivityServiceImplTest {
    private static final String validHead = "type,fruit,quantity";
    private ActivityService activityService;
    private List<String> activities;
    private ActivityTypeStrategy activityStrategy;

    @Before
    public void setUp() {
        activityService = new ActivityServiceImpl();
        Map<ActivityType, ActivityHandler> handlerMap = new HashMap<>();
        handlerMap.put(ActivityType.b, new BalanceHandler());
        handlerMap.put(ActivityType.p, new PurchaseHandler());
        activityStrategy = new ActivityTypeStrategyImpl(handlerMap);
        activities = new ArrayList<>();
        activities.add(validHead);
        activities.add("b,banana,20");
        activities.add("b,apple,100");
        activities.add("p,banana,10");

    }

    @Test
    public void processActivitiesWorkabilityOk() {
        Map<String, Integer> mapExpected
                = Map.of("banana", 10, "apple", 100);
        Map<String, Integer> mapActual
                = activityService.processActivities(activityStrategy, activities);
        Assert.assertEquals(mapExpected, mapActual);
    }
}

package core.basesyntax.service;

import core.basesyntax.model.ActivityType;
import core.basesyntax.service.activityhandler.ActivityHandler;
import core.basesyntax.service.activityhandler.BalanceHandler;
import core.basesyntax.service.activityhandler.PurchaseHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActivityTypeStrategyImplTest {
    private static final String notSupportedActivityType
            = "Given activity type is not supported";
    private ActivityTypeStrategy activityTypeStrategy;
    private Map<ActivityType, ActivityHandler> handlerMap;

    @Before
    public void setUp() {
        handlerMap = new HashMap<>();
        handlerMap.put(ActivityType.b, new BalanceHandler());
        handlerMap.put(ActivityType.p, new PurchaseHandler());
        activityTypeStrategy = new ActivityTypeStrategyImpl(handlerMap);
    }

    @Test
    public void activityTypeStrategyImplWorkabilityOk() {
        Assert.assertEquals(BalanceHandler.class,
                activityTypeStrategy.get(ActivityType.b).getClass());
    }

    @Test
    public void activityTypeStrategyImplInvalidActivityTypeNotOk() {
        try {
            activityTypeStrategy.get(ActivityType.r);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(notSupportedActivityType, e.getMessage());
        }
    }
}

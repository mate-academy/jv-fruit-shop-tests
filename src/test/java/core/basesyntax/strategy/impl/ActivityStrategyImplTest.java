package core.basesyntax.strategy.impl;

import core.basesyntax.model.ActivityType;
import core.basesyntax.strategy.ActivityHandler;
import core.basesyntax.strategy.ActivityStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyImplTest {
    private static ActivityStrategy activityStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<ActivityType, ActivityHandler> activityMap = new HashMap<>();
        activityMap.put(ActivityType.BALANCE, new BalanceActivityHandler());
        activityMap.put(ActivityType.PURCHASE, new PurchaceActivityHandler());
        activityMap.put(ActivityType.RETURN, new ReturnActivityHandler());
        activityMap.put(ActivityType.SUPPLY, new SupplyActivityHandler());
        activityStrategy = new ActivityStrategyImpl(activityMap);
    }

    @Test
    public void getHandler_gettingBalanceActivity_ok() {
        ActivityHandler actual = activityStrategy.getHandler(ActivityType.BALANCE);
        Assert.assertEquals(BalanceActivityHandler.class, actual.getClass());
    }

    @Test
    public void getHandler_gettingPurchaseActivity_ok() {
        ActivityHandler actual = activityStrategy.getHandler(ActivityType.PURCHASE);
        Assert.assertEquals(PurchaceActivityHandler.class, actual.getClass());
    }

    @Test
    public void getHandler_gettingReturnActivity_ok() {
        ActivityHandler actual = activityStrategy.getHandler(ActivityType.RETURN);
        Assert.assertEquals(ReturnActivityHandler.class, actual.getClass());
    }

    @Test
    public void getHandler_gettingSupplyActivity_ok() {
        ActivityHandler actual = activityStrategy.getHandler(ActivityType.SUPPLY);
        Assert.assertEquals(SupplyActivityHandler.class, actual.getClass());
    }
}

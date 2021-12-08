package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.ActivityType;
import core.basesyntax.strategy.activities.BalanceService;
import core.basesyntax.strategy.activities.PurchaseService;
import core.basesyntax.strategy.activities.ReturnService;
import core.basesyntax.strategy.activities.SupplyService;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyTest {
    private static ActivityStrategy activityStrategy;

    @BeforeClass
    public static void setup() {
        activityStrategy = new ActivityStrategyImpl();
    }

    @Test
    public void getActivity_getActivityStrategy_OK() {
        assertEquals(BalanceService.class, activityStrategy
                .getActivity(ActivityType.BALANCE)
                .getClass());
        assertEquals(PurchaseService.class, activityStrategy
                .getActivity(ActivityType.PURCHASE)
                .getClass());
        assertEquals(ReturnService.class, activityStrategy
                .getActivity(ActivityType.RETURN)
                .getClass());
        assertEquals(SupplyService.class, activityStrategy
                .getActivity(ActivityType.SUPPLY)
                .getClass());
    }
}

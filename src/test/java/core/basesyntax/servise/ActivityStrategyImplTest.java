package core.basesyntax.servise;

import core.basesyntax.lib.ActivitiesEnum;
import core.basesyntax.servise.activity.ActivityHandler;
import core.basesyntax.servise.activity.BalanceActivityHandlerImpl;
import core.basesyntax.servise.activity.PurchaseActivityHandlerImpl;
import core.basesyntax.servise.activity.ReturnActivityHandlerImpl;
import core.basesyntax.servise.activity.SupplyActivityHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyImplTest {
    private static Map<String, ActivityHandler> activities;
    private static final String UNKNOWN_ACTIVITY = "0x#";
    private static ActivityStrategy activityStrategy;

    @BeforeClass
    public static void beforeClass() {
        activities = new HashMap<>();
        activities.put(ActivitiesEnum.BALANCE.getActivity(), new BalanceActivityHandlerImpl());
        activities.put(ActivitiesEnum.SUPPLY.getActivity(), new SupplyActivityHandlerImpl());
        activities.put(ActivitiesEnum.PURCHASE.getActivity(), new PurchaseActivityHandlerImpl());
        activities.put(ActivitiesEnum.RETURN.getActivity(), new ReturnActivityHandlerImpl());
        activityStrategy = new ActivityStrategyImpl(activities);
    }

    @Test
    public void getSupplyActivityHandlerImpl_Ok() {
        Class<?> expected = activityStrategy.getActivity(ActivitiesEnum.SUPPLY.getActivity())
                .getClass();
        Class<?> actual = SupplyActivityHandlerImpl.class;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getUnknownActivity_NotOk() {
        activityStrategy.getActivity(UNKNOWN_ACTIVITY);
    }
}

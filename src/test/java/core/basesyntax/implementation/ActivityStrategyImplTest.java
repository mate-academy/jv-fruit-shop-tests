package core.basesyntax.implementation;

import core.basesyntax.activity.Activities;
import core.basesyntax.activity.ActivityHandler;
import core.basesyntax.activity.ActivityStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyImplTest {
    private static Map<String, ActivityHandler> activities;
    private static ActivityStrategy activityStrategy;

    @BeforeClass
    public static void beforeClass() {
        activities = new HashMap<>();
        activities.put(Activities.BALANCE.getActivity(), new BalanceActivityHandlerImpl());
        activities.put(Activities.RETURN.getActivity(), new ReturnActivityHandlerImpl());
        activities.put(Activities.PURCHASE.getActivity(), new PurchaseActivityHandlerImpl());
        activities.put(Activities.SUPPLY.getActivity(), new SupplyActivityHandlerImpl());
        activityStrategy = new ActivityStrategyImpl(activities);

    }

    @Test
    public void getActivity_BalanceActivity_Ok() {
        Class<?> expected = activityStrategy
                .getActivity(Activities.BALANCE.getActivity()).getClass();
        Class<?> actual = BalanceActivityHandlerImpl.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getActivity_ReturnActivity_Ok() {
        Class<?> expected = activityStrategy.getActivity(Activities.RETURN.getActivity())
                .getClass();
        Class<?> actual = ReturnActivityHandlerImpl.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getActivity_SupplyActivity_Ok() {
        Class<?> expected = activityStrategy.getActivity(Activities.SUPPLY.getActivity())
                .getClass();
        Class<?> actual = SupplyActivityHandlerImpl.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getActivity_PurchaseActivity_Ok() {
        Class<?> expected = activityStrategy.getActivity(Activities.PURCHASE.getActivity())
                .getClass();
        Class<?> actual = PurchaseActivityHandlerImpl.class;
        Assert.assertEquals(expected, actual);
    }
}

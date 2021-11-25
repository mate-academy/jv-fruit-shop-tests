package core.basesyntax.startegy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.startegy.ActivityHandler;
import core.basesyntax.startegy.ActivityStrategy;
import core.basesyntax.startegy.ActivityType;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyImplTest {
    private static FruitDao fruitDao;
    private static Map<ActivityType, ActivityHandler> activityMap;
    private static ActivityStrategy activityStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        activityMap = new HashMap<>();
        activityMap.put(ActivityType.BALANCE, new BalanceActivityHandler(fruitDao));
        activityMap.put(ActivityType.PURCHASE, new PurchaseActivityHandler(fruitDao));
        activityMap.put(ActivityType.RETURN, new AddActivityHandler(fruitDao));
        activityMap.put(ActivityType.SUPPLY, new AddActivityHandler(fruitDao));
        activityStrategy = new ActivityStrategyImpl(activityMap);
    }

    @Test
    public void get_balanceActivity_ok() {
        ActivityHandler expended = new BalanceActivityHandler(fruitDao);
        ActivityHandler actual = activityStrategy.get(ActivityType.BALANCE);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }

    @Test
    public void get_purchaseActivity_ok() {
        ActivityHandler expended = new PurchaseActivityHandler(fruitDao);
        ActivityHandler actual = activityStrategy.get(ActivityType.PURCHASE);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }

    @Test
    public void get_supplyActivity_ok() {
        ActivityHandler expended = new AddActivityHandler(fruitDao);
        ActivityHandler actual = activityStrategy.get(ActivityType.SUPPLY);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }

    @Test
    public void get_returnActivity_ok() {
        ActivityHandler expended = new AddActivityHandler(fruitDao);
        ActivityHandler actual = activityStrategy.get(ActivityType.RETURN);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }
}

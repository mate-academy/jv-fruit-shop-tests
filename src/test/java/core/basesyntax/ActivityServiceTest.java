package core.basesyntax;

import static org.hamcrest.CoreMatchers.startsWith;

import core.basesyntax.strategy.ActivityService;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.impl.BalanceService;
import core.basesyntax.strategy.impl.PurchaseService;
import core.basesyntax.strategy.impl.ReturnService;
import core.basesyntax.strategy.impl.SupplyService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ActivityServiceTest {

    private static final Map<String, ActivityService> activityHandlerMap = new HashMap<>();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testActivityService_wrongActivity() {

        activityHandlerMap.put("b", new BalanceService());
        activityHandlerMap.put("s", new SupplyService());
        ActivityStrategy activityStrategy = new ActivityStrategy(activityHandlerMap);

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("Undefined activity"));
        activityStrategy.getActivityService("p").getNewCount(3, 8);
    }

    @Test
    public void testActivityService_exception() {

        activityHandlerMap.put("b", new BalanceService());
        activityHandlerMap.put("s", new SupplyService());
        activityHandlerMap.put("p", new PurchaseService());
        activityHandlerMap.put("r", new ReturnService());
        ActivityStrategy activityStrategy = new ActivityStrategy(activityHandlerMap);

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("Not enough fruit!"));
        activityStrategy.getActivityService("p").getNewCount(3, 8);
    }

    @Test
    public void testActivityService() {

        Map<String, ActivityService> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put("b", new BalanceService());
        activityHandlerMap.put("s", new SupplyService());
        activityHandlerMap.put("p", new PurchaseService());
        activityHandlerMap.put("r", new ReturnService());
        ActivityStrategy activityStrategy = new ActivityStrategy(activityHandlerMap);
        Assert.assertEquals(Integer.valueOf(5),
                activityStrategy.getActivityService("b").getNewCount(3, 5));
        Assert.assertEquals(Integer.valueOf(8),
                activityStrategy.getActivityService("s").getNewCount(3, 5));
        Assert.assertEquals(Integer.valueOf(2),
                activityStrategy.getActivityService("p").getNewCount(5, 3));
        Assert.assertEquals(Integer.valueOf(8),
                activityStrategy.getActivityService("r").getNewCount(3, 5));
    }
}

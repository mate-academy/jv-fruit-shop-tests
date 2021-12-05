package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.service.ActivityStrategy;
import core.basesyntax.service.ActivityStrategyImpl;
import core.basesyntax.service.activityhandler.ActivityHandler;
import core.basesyntax.service.activityhandler.BalanceActivityHandler;
import core.basesyntax.service.activityhandler.PurchaseActivityHandler;
import core.basesyntax.service.activityhandler.ReturnActivityHandler;
import core.basesyntax.service.activityhandler.SupplyActivityHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyImplTest {
    private static final Map<String, ActivityHandler> activityHandlerMap = new HashMap<>();
    private static final ActivityStrategy activityStrategy =
            new ActivityStrategyImpl(activityHandlerMap);
    private static final String BALANCE_ACTIVITY = "b";
    private static final String PURCHASE_ACTIVITY = "p";
    private static final String RETURN_ACTIVITY = "r";
    private static final String SUPPLY_ACTIVITY = "s";
    private static final String INCORRECT_ACTIVITY = "!";

    @BeforeClass
    public static void beforeClass() {
        activityHandlerMap.put(BALANCE_ACTIVITY, new BalanceActivityHandler());
        activityHandlerMap.put(PURCHASE_ACTIVITY, new PurchaseActivityHandler());
        activityHandlerMap.put(RETURN_ACTIVITY, new ReturnActivityHandler());
        activityHandlerMap.put(SUPPLY_ACTIVITY, new SupplyActivityHandler());
    }

    @Test
    public void activityStrategy_Constructor_Ok() {
        assertEquals(ActivityStrategyImpl.class,
                new ActivityStrategyImpl(activityHandlerMap).getClass());
    }

    @Test
    public void activityStrategy_getHandler_Ok() {
        assertEquals(BalanceActivityHandler.class,
                activityStrategy.get(BALANCE_ACTIVITY).getClass());
        assertEquals(PurchaseActivityHandler.class,
                activityStrategy.get(PURCHASE_ACTIVITY).getClass());
        assertEquals(ReturnActivityHandler.class,
                activityStrategy.get(RETURN_ACTIVITY).getClass());
        assertEquals(SupplyActivityHandler.class,
                activityStrategy.get(SUPPLY_ACTIVITY).getClass());
    }

    @Test (expected = RuntimeException.class)
    public void activityStrategy_getHandler_Not_Ok() {
        activityStrategy.get(INCORRECT_ACTIVITY);
    }

    @Test
    public void activityHandlerMap_Not_Ok() {
        assertNull(activityHandlerMap.get(INCORRECT_ACTIVITY));
    }
}

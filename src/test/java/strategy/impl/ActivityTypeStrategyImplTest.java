package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import model.ActivityType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.ActivityTypeHandler;
import strategy.ActivityTypeStrategy;

public class ActivityTypeStrategyImplTest {
    private static ActivityTypeStrategy activityTypeStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<ActivityType, ActivityTypeHandler> activityTypeHandlerMap = new HashMap<>();
        activityTypeHandlerMap.put(ActivityType.BALANCE, new BalanceTypeHandler());
        activityTypeHandlerMap.put(ActivityType.SUPPLY, new AdditionTypeHandler());
        activityTypeHandlerMap.put(ActivityType.RETURN, new AdditionTypeHandler());
        activityTypeHandlerMap.put(ActivityType.PURCHASE, new PurchaseTypeHandler());

        activityTypeStrategy = new ActivityTypeStrategyImpl(
                activityTypeHandlerMap);
    }

    @Test
    public void nullType_NotOk() {
        assertThrows(RuntimeException.class, () ->
                System.out.println(activityTypeStrategy.get(null)));
    }

    @Test
    public void existingType_Ok() {
        assertEquals(BalanceTypeHandler.class,
                activityTypeStrategy.get(ActivityType.BALANCE).getClass(),
                "Wrong type handler");
    }
}

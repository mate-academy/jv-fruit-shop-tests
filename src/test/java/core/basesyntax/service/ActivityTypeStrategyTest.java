package core.basesyntax.service;

import core.basesyntax.model.ActivityType;
import core.basesyntax.service.activityhandler.ActivityHandler;
import core.basesyntax.service.activityhandler.BalanceHandler;
import core.basesyntax.service.activityhandler.PurchaseHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityTypeStrategyTest {
    private static final String notSupportedActivityType
            = "Given activity type is not supported";
    private ActivityTypeStrategy activityTypeStrategy;
    private Map<ActivityType, ActivityHandler> handlerMap;

    @BeforeEach
    void setUp() {
        handlerMap = new HashMap<>();
        handlerMap.put(ActivityType.b, new BalanceHandler());
        handlerMap.put(ActivityType.p, new PurchaseHandler());
        activityTypeStrategy = new ActivityTypeStrategyImpl(handlerMap);
    }

    @Test
    void activityTypeStrategyImplWorkabilityOk() {
        Assertions.assertEquals(BalanceHandler.class,
                activityTypeStrategy.get(ActivityType.b).getClass());
    }

    @Test
    void activityTypeStrategyImplInvalidActivityTypeNotOk() {
        IllegalArgumentException exception
                = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> activityTypeStrategy.get(ActivityType.r));
        Assertions.assertEquals(notSupportedActivityType, exception.getMessage());
    }
}

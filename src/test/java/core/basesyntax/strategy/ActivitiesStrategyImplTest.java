package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.activities.ActivitiesHandler;
import core.basesyntax.strategy.activities.BalanceHandler;
import core.basesyntax.strategy.activities.PurchaseHandler;
import core.basesyntax.strategy.activities.ReturnHandler;
import core.basesyntax.strategy.activities.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ActivitiesStrategyImplTest {
    private static ActivitiesStrategy strategy;
    private static Map<String, ActivitiesHandler> activitiesHandlerMap;

    @BeforeAll
    static void beforeAll() {
        activitiesHandlerMap = new HashMap<>();
        activitiesHandlerMap.put("b", new BalanceHandler());
        activitiesHandlerMap.put("s", new SupplyHandler());
        activitiesHandlerMap.put("p", new PurchaseHandler());
        activitiesHandlerMap.put("r", new ReturnHandler());
        strategy = new ActivitiesStrategyImpl(activitiesHandlerMap);
    }

    @Test
    void getBalanceOperationOk() {
        assertEquals(BalanceHandler.class, strategy.get("b").getClass());
    }

    @Test
    void getPurchaseOperationOk() {
        assertEquals(PurchaseHandler.class, strategy.get("p").getClass());
    }

    @Test
    void getReturnOperationOk() {
        assertEquals(ReturnHandler.class, strategy.get("r").getClass());
    }

    @Test
    void getSupplyOperationOk() {
        assertEquals(SupplyHandler.class, strategy.get("s").getClass());
    }

    @Test
    void getOperationByNonValidDataOk() {
        assertEquals(null, strategy.get("v"));
    }

    @Test
    void getOperationByNullOk() {
        assertEquals(null, strategy.get(null));
    }
}

package strategy;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.activities.ActivitiesHandler;
import strategy.activities.BalanceHandler;
import strategy.activities.PurchaseHandler;
import strategy.activities.ReturnHandler;
import strategy.activities.SupplyHandler;

public class ActivitiesStrategyImplTest {
    private static Map<String, ActivitiesHandler> activitiesHandlerMap = new HashMap<>();
    private static ActivitiesStrategy strategy = new ActivitiesStrategyImpl(activitiesHandlerMap);

    @BeforeClass
    public static void beforeClass() {
        activitiesHandlerMap.put("b", new BalanceHandler());
        activitiesHandlerMap.put("s", new SupplyHandler());
        activitiesHandlerMap.put("p", new PurchaseHandler());
        activitiesHandlerMap.put("r", new ReturnHandler());
    }

    @Test
    public void getBalanceOperationOk() {
        assertEquals(BalanceHandler.class, strategy.get("b").getClass());
    }

    @Test
    public void getPurchaseOperationOk() {
        assertEquals(PurchaseHandler.class, strategy.get("p").getClass());
    }

    @Test
    public void getReturnOperationOk() {
        assertEquals(ReturnHandler.class, strategy.get("r").getClass());
    }

    @Test
    public void getSupplyOperationOk() {
        assertEquals(SupplyHandler.class, strategy.get("s").getClass());
    }

    @Test
    public void getOperationByNonValidDataOk() {
        assertEquals(null, strategy.get("v"));
    }

    @Test
    public void getOperationByNullOk() {
        assertEquals(null, strategy.get(null));
    }
}

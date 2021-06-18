package core.basesyntax.services.actions;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private Map<String, ActionHandler> actionHandlerMap;

    @Before
    public void before() {
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("b",new BalanceHandler());
        actionHandlerMap.put("s",new IncreaseHandler());
        actionHandlerMap.put("p",new PurchaseHandler());
        actionHandlerMap.put("r",new IncreaseHandler());
    }

    @Test
    public void purchaseHandler_Ok() {
        String type = "p";
        String fruitName = "apple";
        int quantity = 20;
        int expected = -20;
        ActionHandler actionHandler = actionHandlerMap.get(type);
        int actual = actionHandler.getResultOfAction(fruitName, quantity);
        Assert.assertEquals(expected, actual);
    }
}

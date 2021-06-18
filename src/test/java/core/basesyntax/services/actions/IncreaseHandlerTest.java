package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IncreaseHandlerTest {
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
    public void testGetResultOfAction_Ok() {
        String type = "s";
        String fruitName = "banana";
        int quantity = 40;
        ActionHandler actionHandler = actionHandlerMap.get(type);
        actionHandler.getResultOfAction(fruitName, quantity);
        int actual = Storage.fruits.get(fruitName);
        int expected = 192;
        Assert.assertEquals(actual, expected);
    }
}

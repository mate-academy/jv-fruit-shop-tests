package shop.service.impl;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDaoImpl;
import shop.service.action.ActionHandler;
import shop.service.action.ActionStrategyHandler;
import shop.service.action.IncreaseActionHandler;

public class ActionStrategyHandlerImplTest {
    private static HashMap<String, ActionHandler> actionMap = new HashMap<>();
    private static ActionStrategyHandler actionStrategyHandler;

    @BeforeClass
    public static void beforeAll() {
        actionMap.put("s", new IncreaseActionHandler(new FruitDaoImpl()));
        actionStrategyHandler = new ActionStrategyHandlerImpl(actionMap);
    }

    @Test
    public void strategy_get_ok() {
        Assert.assertEquals(actionStrategyHandler.get("s").getClass(), IncreaseActionHandler.class);
    }

    @Test(expected = NullPointerException.class)
    public void strategy_get_not_ok() {
        Assert.assertNotEquals(actionStrategyHandler.get("b").getClass(),
                IncreaseActionHandler.class);
    }
}

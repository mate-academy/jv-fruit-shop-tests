package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static Map<String, ActionHandler> actionHandlerMap;

    @BeforeClass
    public static void setUp() {
        Storage.fruits.clear();
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("p",new PurchaseHandler());
    }

    @Before
    public void init() {
        Storage.fruits.put("apple", 40);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_Not_Ok() {
        String type = "p";
        String fruitName = "apple";
        int quantity = 50;
        ActionHandler actionHandler = actionHandlerMap.get(type);
        actionHandler.getResultOfAction(fruitName, quantity);
    }

    @Test()
    public void purchaseHandler_Ok() {
        String type = "p";
        String fruitName = "apple";
        int quantity = 20;
        int expected = 20;
        ActionHandler actionHandler = actionHandlerMap.get(type);
        int actual = actionHandler.getResultOfAction(fruitName, quantity);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}

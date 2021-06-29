package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncreaseHandlerTest {
    private static Map<String, ActionHandler> actionHandlerMap;

    @BeforeClass
    public static void setUp() {
        Storage.fruits.clear();
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("s",new IncreaseHandler());
    }

    @Test
    public void testGetResultOfAction_Ok() {
        String type = "s";
        String fruitName = "banana";
        int quantity = 40;
        ActionHandler actionHandler = actionHandlerMap.get(type);
        int actual = actionHandler.getResultOfAction(fruitName, quantity);
        int expected = 40;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}

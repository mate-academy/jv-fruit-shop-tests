package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncreaseHandlerTest {
    private static ActionHandler actionHandler;

    @BeforeClass
    public static void setUp() {
        Storage.fruits.clear();
        actionHandler = new IncreaseHandler();
    }

    @Test
    public void testGetResultOfAction_Ok() {
        String fruitName = "banana";
        int quantity = 40;
        int actual = actionHandler.getResultOfAction(fruitName, quantity);
        int expected = 40;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}

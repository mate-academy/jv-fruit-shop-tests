package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static ActionHandler actionHandler;

    @BeforeClass
    public static void setUp() {
        Storage.fruits.clear();
        actionHandler = new PurchaseHandler();
    }

    @Before
    public void init() {
        Storage.fruits.put("apple", 40);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_Not_Ok() {
        String fruitName = "apple";
        int quantity = 50;
        actionHandler.getResultOfAction(fruitName, quantity);
    }

    @Test()
    public void purchaseHandler_Ok() {
        String fruitName = "apple";
        int quantity = 20;
        int expected = 20;
        int actual = actionHandler.getResultOfAction(fruitName, quantity);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}

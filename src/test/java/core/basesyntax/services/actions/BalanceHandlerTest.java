package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static ActionHandler actionHandler;

    @BeforeClass
    public static void setUp() {
        Storage.fruits.clear();
        actionHandler = new BalanceHandler();
    }

    @Test
    public void testGetResultOfAction_Ok() {
        String fruitName = "banana";
        int quantity = 40;
        actionHandler.getResultOfAction(fruitName, quantity);
        Set<Map.Entry<String, Integer>> actual = Storage.fruits.entrySet();

        Map<String, Integer> fruitsTestStorage = new HashMap<>();
        fruitsTestStorage.put("banana", 40);
        Set<Map.Entry<String, Integer>> expected = fruitsTestStorage.entrySet();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}

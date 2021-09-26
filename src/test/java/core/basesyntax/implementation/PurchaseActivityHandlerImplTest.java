package core.basesyntax.implementation;

import core.basesyntax.Storage;
import core.basesyntax.activity.ActivityHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseActivityHandlerImplTest {
    private static final String FRUIT = "peach";
    private static final int AMOUNT = 100;
    private final Map<String, Integer> testMap = new HashMap<>();
    private final ActivityHandler purchaseHandler = new PurchaseActivityHandlerImpl();

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void purchase_Ok() {
        Storage.fruitStorage.put(FRUIT, 100);
        testMap.put(FRUIT, 0);
        purchaseHandler.activity(FRUIT, AMOUNT);
        Assert.assertEquals(testMap, Storage.fruitStorage);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseBeforeBalance_NotOk() {
        purchaseHandler.activity(FRUIT, AMOUNT);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseIncorrectAmount_NotOk() {
        Storage.fruitStorage.put(FRUIT, 0);
        purchaseHandler.activity(FRUIT, AMOUNT);
    }
}

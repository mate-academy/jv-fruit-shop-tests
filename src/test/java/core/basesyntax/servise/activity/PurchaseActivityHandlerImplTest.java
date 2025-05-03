package core.basesyntax.servise.activity;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseActivityHandlerImplTest {
    private static final String FRUIT = "banana";
    private static final String QUANTITY = "10";
    private Map<String, Integer> testMap = new HashMap<>();
    private ActivityHandler purchaseActivityHandler = new PurchaseActivityHandlerImpl();

    @Before
    public void setUp() {
        Storage.fruitsDataBase.clear();
    }

    @After
    public void tearDown() {
        Storage.fruitsDataBase.clear();
    }

    @Test
    public void doPurchaseActivity_Ok() {
        Storage.fruitsDataBase.put(FRUIT, Integer.valueOf(QUANTITY));
        testMap.put(FRUIT, 0);
        purchaseActivityHandler.act(FRUIT, QUANTITY);
        Assert.assertEquals(testMap, Storage.fruitsDataBase);
    }

    @Test(expected = RuntimeException.class)
    public void doPurchaseActivityBeforeBalanceActivity_NotOk() {
        purchaseActivityHandler.act(FRUIT, QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void doPurchaseActivityWithDoNotEnoughQuantity_NotOk() {
        Storage.fruitsDataBase.put(FRUIT, 0);
        purchaseActivityHandler.act(FRUIT, QUANTITY);
    }
}

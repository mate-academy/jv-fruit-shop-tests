package core.basesyntax.servise.activity;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseActivityHandlerImplTest {
    private static final String FRUIT = "banana";
    private static final String QUANTITY = "10";
    private Map<String, Integer> testMap = new HashMap<>();
    private ActivityHandler purchaseActivityHandler = new PurchaseActivityHandlerImpl();

    @Test
    public void doPurchaseActivity_Ok() {
        Storage.fruitsDataBase.put(FRUIT, Integer.valueOf(QUANTITY));
        testMap.put(FRUIT, 0);
        purchaseActivityHandler.act(FRUIT, QUANTITY);
        Assert.assertTrue(testMap.equals(Storage.fruitsDataBase));
        Storage.fruitsDataBase.clear();
    }

    @Test(expected = RuntimeException.class)
    public void doPurchaseActivityBeforeBalanceActivity_NotOk() {
        Storage.fruitsDataBase.clear();
        purchaseActivityHandler.act(FRUIT, QUANTITY);
        Storage.fruitsDataBase.clear();
    }

    @Test(expected = RuntimeException.class)
    public void doPurchaseActivityWithDoNotEnoughQuantity_NotOk() {
        Storage.fruitsDataBase.put(FRUIT, 0);
        purchaseActivityHandler.act(FRUIT, QUANTITY);
        Storage.fruitsDataBase.clear();
    }
}

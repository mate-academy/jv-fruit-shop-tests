package core.basesyntax.servise.activity;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class SupplyActivityHandlerImplTest {
    private static final String FRUIT = "banana";
    private static final String QUANTITY = "10";
    private Map<String, Integer> testMap = new HashMap<>();
    private ActivityHandler supplyActivityHandler = new SupplyActivityHandlerImpl();

    @Test
    public void doSupplyActivity_Ok() {
        Storage.fruitsDataBase.put(FRUIT, Integer.valueOf(QUANTITY));
        testMap.put(FRUIT, Integer.parseInt(QUANTITY) * 2);
        supplyActivityHandler.act(FRUIT, QUANTITY);
        Assert.assertTrue(testMap.equals(Storage.fruitsDataBase));
        Storage.fruitsDataBase.clear();
    }

    @Test(expected = RuntimeException.class)
    public void doSupplyActivityBeforeBalanceActivity_NotOk() {
        supplyActivityHandler.act(FRUIT, QUANTITY);
        Storage.fruitsDataBase.clear();
    }
}

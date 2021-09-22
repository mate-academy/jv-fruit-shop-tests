package core.basesyntax.servise.activity;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class BalanceActivityHandlerImplTest {
    private static final String FRUIT = "banana";
    private static final String QUANTITY = "10";
    private Map<String, Integer> testMap = new HashMap<>();
    private ActivityHandler balanceActivityHandler = new BalanceActivityHandlerImpl();

    @Test
    public void doBalanceActivity_Ok() {
        testMap.put(FRUIT, Integer.valueOf(QUANTITY));
        balanceActivityHandler.act(FRUIT, QUANTITY);
        Assert.assertTrue(testMap.equals(Storage.fruitsDataBase));
        Storage.fruitsDataBase.clear();
    }

    @Test(expected = RuntimeException.class)
    public void doBalanceActivityAfterAnotherActivity_NotOk() {
        Storage.fruitsDataBase.put(FRUIT, Integer.valueOf(QUANTITY));
        balanceActivityHandler.act(FRUIT, QUANTITY);
        Storage.fruitsDataBase.clear();
    }
}

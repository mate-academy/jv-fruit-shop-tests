package core.basesyntax.servise.activity;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ReturnActivityHandlerImplTest {
    private static final String FRUIT = "banana";
    private static final String QUANTITY = "10";
    private Map<String, Integer> testMap = new HashMap<>();
    private ActivityHandler returnActivityHandler = new ReturnActivityHandlerImpl();

    @Test
    public void doReturnActivity_Ok() {
        Storage.fruitsDataBase.put(FRUIT, Integer.valueOf(QUANTITY));
        testMap.put(FRUIT, Integer.parseInt(QUANTITY) * 2);
        returnActivityHandler.act(FRUIT, QUANTITY);
        Assert.assertTrue(testMap.equals(Storage.fruitsDataBase));
        Storage.fruitsDataBase.clear();
    }

    @Test(expected = RuntimeException.class)
    public void doReturnActivityBeforeBalanceActivity_NotOk() {
        Storage.fruitsDataBase.clear();
        returnActivityHandler.act(FRUIT, QUANTITY);
        Storage.fruitsDataBase.clear();
    }
}

package core.basesyntax.implementation;

import core.basesyntax.Storage;
import core.basesyntax.activity.ActivityHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceActivityHandlerImplTest {
    private static final String FRUIT = "peach";
    private static final int AMOUNT = 100;
    private final Map<String, Integer> testMap = new HashMap<>();
    private final ActivityHandler balanceActivityHandler = new BalanceActivityHandlerImpl();

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void balanceActivity_Ok() {
        testMap.put(FRUIT, AMOUNT);
        balanceActivityHandler.activity(FRUIT,AMOUNT);
        Assert.assertEquals(testMap, Storage.fruitStorage);
    }

    @Test(expected = RuntimeException.class)
    public void balanceIsNotFirstActivity_NotOk() {
        Storage.fruitStorage.put(FRUIT, AMOUNT);
        balanceActivityHandler.activity(FRUIT, AMOUNT);
    }
}

package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.Storage;
import core.basesyntax.strategy.PurchaseStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseStrategyImplTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final Integer DEFAULT_QUANTITY = 10;
    private static PurchaseStrategy purchaseStrategy;
    private Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        purchaseStrategy = new PurchaseStrategyImpl();
    }

    @Before
    public void init() {
        Storage.fruits.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        expected = new HashMap<>();
    }

    @Test
    public void action_ExistFruit_Ok() {
        purchaseStrategy.action(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void action_NonExistentFruit_Ok() {
        purchaseStrategy.action("kiwi", 5);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        expected.put("kiwi", -5);
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}

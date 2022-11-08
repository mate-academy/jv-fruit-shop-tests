package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.strategy.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionHandlerImplTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final Integer DEFAULT_QUANTITY = 10;
    private static TransactionHandler transactionHandler;
    private Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        transactionHandler = new TransactionHandlerImpl();
    }

    @Before
    public void init() {
        ShopStorage.fruitsStorage.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        expected = new HashMap<>();
    }

    @Test
    public void addToBalance_existFruit_isOk() {
        transactionHandler.addToBalance(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        expected.put(DEFAULT_FRUIT, 20);
        assertEquals(expected, actual);
    }

    @Test
    public void addToBalance_notExistentFruit_isOk() {
        transactionHandler.addToBalance("kiwi", 5);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        expected.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        expected.put("kiwi", 5);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addToBalance_nullFruit_notOk() {
        transactionHandler.addToBalance(null, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void addToBalance_nullQuantity_notOk() {
        transactionHandler.addToBalance(DEFAULT_FRUIT, null);
    }

    @Test
    public void takeFromBalance_existFruit_isOk() {
        transactionHandler.takeFromBalance(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        expected.put(DEFAULT_FRUIT, 0);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void takeFromBalance_notExistentFruit_notOk() {
        transactionHandler.takeFromBalance("kiwi", 5);
    }

    @Test(expected = RuntimeException.class)
    public void takeFromBalance_nullFruit_notOk() {
        transactionHandler.takeFromBalance(null, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void takeFromBalance_nullQuantity_notOk() {
        transactionHandler.takeFromBalance(DEFAULT_FRUIT, null);
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }
}

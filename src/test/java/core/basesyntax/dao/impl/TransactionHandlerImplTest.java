package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.TransactionHandler;
import core.basesyntax.dao.exception.NoSuchAFruitAtShop;
import core.basesyntax.db.Storage;
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
        Storage.fruits.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        expected = new HashMap<>();
    }

    @Test
    public void addToBalance_ExistFruit_Ok() {
        transactionHandler.addToBalance(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, 20);
        assertEquals(expected, actual);
    }

    @Test
    public void addToBalance_NonExistentFruit_Ok() {
        transactionHandler.addToBalance("kiwi", 5);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        expected.put("kiwi", 5);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addToBalance_NullFruit_NotOk() {
        transactionHandler.addToBalance(null, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void addToBalance_NullQuantity_NotOk() {
        transactionHandler.addToBalance(DEFAULT_FRUIT, null);
    }

    @Test
    public void takeFromBalance_ExistFruit_Ok() {
        transactionHandler.takeFromBalance(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, 0);
        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchAFruitAtShop.class)
    public void takeFromBalance_NonExistentFruit_NotOk() {
        transactionHandler.takeFromBalance("kiwi", 5);
    }

    @Test(expected = RuntimeException.class)
    public void takeFromBalance_NullFruit_NotOk() {
        transactionHandler.takeFromBalance(null, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void takeFromBalance_NullQuantity_NotOk() {
        transactionHandler.takeFromBalance(DEFAULT_FRUIT, null);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}

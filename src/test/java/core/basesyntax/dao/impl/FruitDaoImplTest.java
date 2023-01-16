package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.FruitStorage;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 50;
    private static final int NEW_QUANTITY = 100;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void beforeEach() {
        FruitStorage.fruitStorage.put(APPLE, DEFAULT_QUANTITY);
    }

    @After
    public void afterEach() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void saveFruit_isOk() {
        fruitDao.saveFruit(BANANA, DEFAULT_QUANTITY);
        int actualSize = FruitStorage.fruitStorage.size();
        int actualQuantity = FruitStorage.fruitStorage.get(BANANA);
        boolean actualContainsKey = FruitStorage.fruitStorage.containsKey(BANANA);
        assertEquals("Storage size expected 2, but was " + actualSize + ".",
                2, actualSize);
        assertTrue("Storage contains key expected true but was false.",
                actualContainsKey);
        assertEquals("Fruit quantity expected " + DEFAULT_QUANTITY
                + " but was " + actualQuantity + ".", DEFAULT_QUANTITY, actualQuantity);
    }

    @Test
    public void saveFruit_sameKey_isOk() {
        fruitDao.saveFruit(APPLE, NEW_QUANTITY);
        int actual = FruitStorage.fruitStorage.size();
        assertEquals(1, actual);
        assertTrue(FruitStorage.fruitStorage.containsKey(APPLE));
        int actualQuantity = FruitStorage.fruitStorage.get(APPLE);
        assertEquals(NEW_QUANTITY, actualQuantity);
    }

    @Test
    public void getQuantity_isOk() {
        int actual = fruitDao.getQuantity(APPLE);
        assertEquals(DEFAULT_QUANTITY, actual);
    }

    @Test
    public void getQuantity_nonExistingKey_notOk() {
        Integer actual = fruitDao.getQuantity(BANANA);
        assertNull(actual);
    }

    @Test
    public void getAll_isOk() {
        Map<String, Integer> expected = FruitStorage.fruitStorage;
        Map<String, Integer> actual = fruitDao.getAll();
        assertSame(expected, actual);
    }
}

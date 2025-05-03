package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NoFruitsException;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitsDaoImplTest {
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitsDaoImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.put("banana", 50);
    }

    @After
    public void afterClass() {
        Storage.fruits.clear();
    }

    @Test
    public void add_addFruit_Ok() {
        fruitsDao.add("apple", 40);
        int actual = Storage.fruits.size();
        assertEquals(2, actual);
    }

    @Test
    public void getFruitQuantity_Ok() {
        int actual = fruitsDao.getFruitQuantity("banana");
        assertEquals(50, actual);
    }

    @Test(expected = NoFruitsException.class)
    public void getFruitQuantity_noFruitsInShop_NotOk() {
        fruitsDao.getFruitQuantity("apple");
    }

    @Test
    public void getAll_Ok() {
        fruitsDao.add("apple", 40);
        Map<String, Integer> allFruits = fruitsDao.getAll();
        assertTrue(allFruits.containsKey("banana"));
        assertTrue(allFruits.containsKey("apple"));
        assertTrue(allFruits.containsValue(50));
        assertTrue(allFruits.containsValue(40));
    }
}

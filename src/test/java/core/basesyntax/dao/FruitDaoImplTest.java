package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @BeforeClass
    public static void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple",150);
    }

    @Test
    public void addFruit_Ok() {
        fruitDao.addFruit("banana",500);
        int actual = Storage.fruits.size();
        int expected = 2;
        assertFalse(Storage.fruits.isEmpty());
        assertEquals(actual,expected);
    }

    @Test
    public void getQuantityValid_Ok() {
        Integer expected = fruitDao.getQuantity("apple");
        Integer actual = 150;
        assertEquals(expected,actual);
    }

    @Test
    public void getAll_Ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("apple",150);
        expected.put("banana",500);
        Map<String, Integer> actual = fruitDao.getAll();
        assertEquals(actual, expected);
    }
}

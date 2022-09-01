package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    public static final int DEFAULT_QUANTITY = 10;
    private static Map<Fruit, Integer> expectedFruits;
    private static FruitDao fruitDao;
    private static Fruit fruit;

    @Before
    public void setUp() throws Exception {
        expectedFruits = new HashMap<>();
        fruitDao = new FruitDaoImpl();
        fruit = new Fruit("banana");
    }

    @Test(expected = RuntimeException.class)
    public void set_NullFruit_isNotValid() {
        fruitDao.set(null,DEFAULT_QUANTITY);
    }

    @Test
    public void set_newFruit_isValid() {
        expectedFruits.put(fruit, DEFAULT_QUANTITY);
        fruitDao.set(fruit,DEFAULT_QUANTITY);
        int actual = Storage.fruits.get(fruit);
        int expected = expectedFruits.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullFruit_isNotValid() {
        fruitDao.add(null,DEFAULT_QUANTITY);
    }

    @Test
    public void add_newFruit_isValid() {
        expectedFruits.put(fruit, 12);
        fruitDao.set(fruit,DEFAULT_QUANTITY);
        int actual = Storage.fruits.get(fruit) + 2;
        int expected = expectedFruits.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void subtract_nullKey_isNotValid() {
        fruitDao.subtract(null, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void subtract_quantityoreThanExistsInStorage_isNotValid() {
        fruitDao.subtract(fruit, fruitDao.get(fruit) + 1);
    }

    @Test
    public void subtract_isValid() {
        fruitDao.set(fruit,DEFAULT_QUANTITY);
        int actual = Storage.fruits.get(fruit) - 2;
        assertEquals(8, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_isValid() {
        fruitDao.get(null);
    }

    @Test
    public void getAll() {
        Fruit actualFruit = new Fruit("apple");
        expectedFruits.put(fruit, DEFAULT_QUANTITY);
        expectedFruits.put(actualFruit, DEFAULT_QUANTITY);
        Storage.fruits.put(fruit,DEFAULT_QUANTITY);
        Storage.fruits.put(actualFruit, DEFAULT_QUANTITY);
        Map<Fruit,Integer> actual = fruitDao.getAll();
        assertEquals(expectedFruits, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}

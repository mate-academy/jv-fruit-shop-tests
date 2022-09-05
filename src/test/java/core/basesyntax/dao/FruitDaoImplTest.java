package core.basesyntax.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static core.basesyntax.db.Storage.fruits;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void addFruit_Ok() {
        fruits.put("banana", 80);
        fruits.put("apple", 75);
        fruits.put("grape", 30);
        fruits.put("mango", 16);
        fruitDao.add("mango", 18);
        fruitDao.add("pear", 16);
        long actual = fruits.get("mango");
        Assert.assertEquals(18, actual);
        Assert.assertTrue(fruits.containsKey("pear"));
    }

    @Test
    public void getAmountOfFruit_Ok() {
        fruits.put("banana", 80);
        fruits.put("apple", 75);
        fruits.put("grape", 30);
        fruits.put("mango", 16);
        long actual = fruitDao.getFruitAmount("grape");
        Assert.assertEquals(30, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getAmountOfAbsentFruit_NotOk() {
        fruits.put("banana", 80);
        fruits.put("apple", 75);
        fruits.put("grape", 30);
        fruits.put("mango", 16);
        fruitDao.getFruitAmount("plum");
    }

    @Test (expected = NullPointerException.class)
    public void getNullFruit_NotOk() {
        fruits.put("banana", 80);
        fruits.put("apple", 75);
        fruits.put("grape", 30);
        fruits.put("mango", 16);
        fruitDao.getFruitAmount("null");
    }

    @Test (expected = NullPointerException.class)
    public void getFruitOfEmptyStorage_NotOk() {
        fruitDao.getFruitAmount("plum");
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}

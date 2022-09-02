package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void addFruit_Ok() {
        Storage.fruits.put("banana", 80);
        Storage.fruits.put("apple", 75);
        Storage.fruits.put("grape", 30);
        Storage.fruits.put("mango", 16);
        fruitDao.add("mango", 18);
        fruitDao.add("pear", 16);
        long actual = Storage.fruits.get("mango");
        Assert.assertEquals(18, actual);
        Assert.assertTrue(Storage.fruits.containsKey("pear"));
    }

    @Test
    public void getAmountOfFruit_Ok() {
        Storage.fruits.put("banana", 80);
        Storage.fruits.put("apple", 75);
        Storage.fruits.put("grape", 30);
        Storage.fruits.put("mango", 16);
        long actual = fruitDao.getFruitAmount("grape");
        Assert.assertEquals(30, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getAmountOfAbsentFruit_NotOk() {
        Storage.fruits.put("banana", 80);
        Storage.fruits.put("apple", 75);
        Storage.fruits.put("grape", 30);
        Storage.fruits.put("mango", 16);
        fruitDao.getFruitAmount("plum");
    }

    @Test (expected = NullPointerException.class)
    public void getNullFruit_NotOk() {
        Storage.fruits.put("banana", 80);
        Storage.fruits.put("apple", 75);
        Storage.fruits.put("grape", 30);
        Storage.fruits.put("mango", 16);
        fruitDao.getFruitAmount("null");
    }

    @Test (expected = NullPointerException.class)
    public void getFruitOfEmptyStorage_NotOk() {
        fruitDao.getFruitAmount("plum");
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}

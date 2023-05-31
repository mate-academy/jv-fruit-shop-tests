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
    }

    @Test
    public void getFruitAmount_Ok() {
        int actualQuantity = 30;
        String fruitName = "grape";
        Storage.fruits.put(fruitName, actualQuantity);
        Storage.fruits.put("banana", 80);
        Storage.fruits.put("apple", 75);
        Storage.fruits.put("mango", 16);
        long actual = fruitDao.getFruitAmount("grape");
        Assert.assertEquals(actualQuantity, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getFruitAmount_absentFruit_NotOk() {
        Storage.fruits.put("banana", 80);
        Storage.fruits.put("apple", 75);
        Storage.fruits.put("grape", 30);
        Storage.fruits.put("mango", 16);
        fruitDao.getFruitAmount("plum");
    }

    @Test (expected = RuntimeException.class)
    public void getFruitAmount_nullAmount_NotOk() {
        Storage.fruits.put("banana", 80);
        Storage.fruits.put("grape", null);
        fruitDao.getFruitAmount("grape");
    }

    @Test (expected = RuntimeException.class)
    public void getFruitAmount_emptyStorage_NotOk() {
        fruitDao.getFruitAmount("plum");
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}

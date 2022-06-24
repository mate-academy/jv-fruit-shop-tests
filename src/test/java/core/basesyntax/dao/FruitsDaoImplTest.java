package core.basesyntax.dao;

import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitsDaoImplTest {
    private static FruitsDao fruitsDao;
    private static Map<String, Integer> fruitStorage;

    @BeforeClass
    public static void beforeClass() {
        fruitStorage = new HashMap<>();
        fruitsDao = new FruitsDaoImpl();

    }

    @Test(expected = RuntimeException.class)
    public void get_null_notOk() {
        fruitsDao.get(null);
    }

    @Test(expected = RuntimeException.class)
    public void get_notExistingFruit_notOk() {
        fruitsDao.get("tomato");
    }

    @Test
    public void getCurrentFruitAmount_Ok() {
        fruitsDao.add("cherry", 10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("cherry", 10);
        Map<String, Integer> actual = fruitsDao.getCurrentFruitAmount();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_fruitValue_Ok() {
        fruitsDao.add("cherry", 10);
        int actual = fruitsDao.get("cherry");
        int expected = 10;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullFruit_notOk() {
        fruitsDao.add(null, 150);
    }

    @Test(expected = RuntimeException.class)
    public void add_negativeValue_notOk() {
        fruitsDao.add("apple", -123);
    }

    @Test(expected = RuntimeException.class)
    public void add_zeroValue_notOk() {
        fruitsDao.add("apple", 0);
    }

    @Test
    public void add_normalValue_Ok() {
        Assert.assertTrue(fruitsDao.add("cherry", 10));
    }

    @AfterClass
    public static void afterClass() {
        fruitStorage.clear();
    }
}

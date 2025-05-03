package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Map<String, Integer> expectedStorage;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 555);
        expectedStorage.put("apple", 111);
    }

    @Test
    public void putAndGet_Ok() {
        fruitDao.put("apple", 111);
        Assert.assertEquals(expectedStorage.get("apple"), fruitDao.get("apple"));
    }

    @Test
    public void getAll_Ok() {
        fruitDao.put("banana", 555);
        fruitDao.put("apple", 111);
        Assert.assertEquals(expectedStorage, fruitDao.getAll());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

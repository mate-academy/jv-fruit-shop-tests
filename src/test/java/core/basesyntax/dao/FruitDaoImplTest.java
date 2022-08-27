package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Map<String, Integer> expectedStorage;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        expectedStorage = new HashMap<>();
    }

   @Before
    public void setUp() {
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
        expectedStorage.clear();
        Storage.fruits.clear();
    }
}

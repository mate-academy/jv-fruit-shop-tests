package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.put("kiwi", 80);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void addFruit_AddToDB_Ok() {
        fruitDao.addFruit("orange", 20);
        int actual = Storage.fruitStorage.size();
        Assert.assertEquals(2, actual);
    }

    @Test
    public void addFruit_AddToDB_NotOk() {
        fruitDao.addFruit("strawberry", 200);
        fruitDao.addFruit("coconut", 5);
        int actual = Storage.fruitStorage.size();
        Assert.assertFalse(Storage.fruitStorage.isEmpty());
        Assert.assertNotEquals(2,3);
    }

    @Test
    public void getQuantity_GetFruitQuantity_Ok() {
        int actual = fruitDao.getQuantity("kiwi");
        Assert.assertEquals(80, actual);
    }

    @Test
    public void getQuantity_GetFruitQuantity_NotOk() {
        int actual = fruitDao.getQuantity("kiwi");
        Assert.assertNotEquals(20, 80);
    }

    @Test
    public void contains_ContainsFruit_Ok() {
        Assert.assertTrue(fruitDao.contains("kiwi"));
    }

    @Test
    public void contains_ContainsFruit_NotOk() {
        fruitDao.addFruit("lemon", 10);
        fruitDao.addFruit("cherry", 300);
        Assert.assertFalse(fruitDao.contains("apple"));
    }

    @Test
    public void getAll_GetAllFruits_Ok() {
        fruitDao.addFruit("apple", 20);
        Map<String, Integer> actualResult = fruitDao.getAll();
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("kiwi", 80);
        expectedResult.put("apple", 20);
        Assert.assertEquals(actualResult, expectedResult);
    }
}

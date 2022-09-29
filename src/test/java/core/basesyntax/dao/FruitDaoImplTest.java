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
    private final FruitDao fruitDao = new FruitDaoImpl();

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.put("banana", 80);
    }

    @Test
    public void addFruit_NotEmptyData_Ok() {
        fruitDao.addFruit("kiwi", 20);
        int actual = Storage.fruitStorage.size();
        int expectedSize = 2;
        Assert.assertEquals(expectedSize, actual);
    }

    @Test
    public void addFruit_AddToDB_NotOk() {
        fruitDao.addFruit("kiwi", 200);
        fruitDao.addFruit("lemon", 5);
        int actual = Storage.fruitStorage.size();
        int unexpected = 2;
        Assert.assertFalse(Storage.fruitStorage.isEmpty());
        Assert.assertNotEquals(unexpected, actual);
    }

    @Test
    public void getQuantity_Ok() {
        int actual = fruitDao.getQuantity("banana");
        int expected = 80;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_NotOk() {
        int actual = fruitDao.getQuantity("banana");
        int unexpected = 20;
        Assert.assertNotEquals(unexpected, actual);
    }

    @Test
    public void containsFruit_Ok() {
        Assert.assertTrue(fruitDao.contains("banana"));
    }

    @Test
    public void containsFruit_NotOk() {
        Assert.assertFalse(fruitDao.contains("apple"));
    }

    @Test
    public void getAllFruits_Ok() {
        fruitDao.addFruit("apple", 20);
        Map<String, Integer> actualResult = fruitDao.getAll();
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("banana", 80);
        expectedResult.put("apple", 20);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}

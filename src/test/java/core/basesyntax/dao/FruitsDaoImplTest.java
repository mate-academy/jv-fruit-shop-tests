package core.basesyntax.dao;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitsDaoImplTest {
    private static FruitsDao fruitsDao;
    private static Map<String, Integer> fruitsAtStorage;

    @BeforeClass
    public static void beforeClass() {
        fruitsAtStorage = new HashMap<>();
        fruitsDao = new FruitsDaoImpl(fruitsAtStorage);
    }

    @Before
    public void setUp() {
        fruitsDao.add("banana", 50);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullFruit_notOk() {
        fruitsDao.add(null, 200);
    }

    @Test(expected = RuntimeException.class)
    public void get_nullFruit_notOk() {
        fruitsDao.get(null);
    }

    @Test(expected = RuntimeException.class)
    public void remove_nullFruit_notOk() {
        fruitsDao.remove(null);
    }

    @Test(expected = RuntimeException.class)
    public void add_amountIsLessThanZero() {
        fruitsDao.add("apple", -20);
    }

    @Test(expected = RuntimeException.class)
    public void get_nonExistingValue_notOk() {
        fruitsDao.get("pineapple");
    }

    @Test(expected = RuntimeException.class)
    public void remove_nonExistingValue_notOk() {
        fruitsDao.remove("pineapple");
    }

    @Test
    public void get_existingValue_ok() {
        int actual = fruitsDao.get("banana");
        int expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void remove_existingValue_ok() {
        boolean actual = fruitsDao.remove("banana");
        Assert.assertTrue(actual);
    }

    @Test
    public void getFruitsAndQuantityAsMap_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 50);
        Map<String, Integer> actual = fruitsDao.getFruitsAndQuantityAsMap();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add_normalValue_ok() {
        Assert.assertTrue(fruitsDao.add("apple", 50));
    }

    @Test
    public void isFruitAvailable_checkForAvailability_ok() {
        Assert.assertTrue(fruitsDao.isFruitAvailable("banana"));
    }

    @After
    public void tearDown() {
        fruitsAtStorage.clear();
    }
}

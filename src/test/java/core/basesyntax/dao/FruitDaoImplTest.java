package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Storage storage;
    private static Map<String, Integer> testDataMap =
            new HashMap<>(Map.of("banana", 100, "apple", 50));

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
    }

    @Before
    public void setUp() {
        storage.getFruitStorage().put("banana", 100);
        storage.getFruitStorage().put("apple", 50);
    }

    @Test
    public void put_Ok() {
        fruitDao.put("banana", 100);
        Integer actual = fruitDao.get("banana");
        Integer expected = 100;
        assertEquals("Test failed! expected result 100, but was: " + actual,
                expected, actual);
    }

    @Test
    public void get_Ok() {
        Integer expected = testDataMap.get("banana");
        Integer actual = storage.getFruitStorage().get("banana");
        assertEquals("Test failed! expected result "
                + expected + " but was: " + actual, expected, actual);
    }

    @Test
    public void getAllFruitFromMap_Ok() {
        Map<String, Integer> expected = FruitDaoImplTest.testDataMap;
        Map<String, Integer> actual = fruitDao.getAll();
        assertEquals("Test failed! expected result "
                + expected + " but was: " + actual, expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        storage.getFruitStorage().clear();
        testDataMap.clear();
    }
}

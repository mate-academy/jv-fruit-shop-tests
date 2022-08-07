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
    private static Map<String, Integer> excepted;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        excepted = new HashMap<>();
        excepted.put("banana", 100);
        excepted.put("apple", 50);
    }

    @Before
    public void setUp() {
        storage.getFruitStorage().put("banana", 100);
        storage.getFruitStorage().put("apple", 50);
    }

    @Test
    public void putFruitToMap_Ok() {
        fruitDao.put("banana", 100);
        assertEquals((Integer) 100, fruitDao.get("banana"));
    }

    @Test
    public void getOneFruitFromMap_Ok() {
        assertEquals(excepted.get("banana"), storage.getFruitStorage().get("banana"));
    }

    @Test
    public void getAllFruitFromMap_Ok() {
        assertEquals(excepted, fruitDao.getAll());
    }

    @AfterClass
    public static void afterClass() {
        storage.getFruitStorage().clear();
        excepted.clear();
    }
}

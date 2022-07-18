package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopDaoImplTest {
    private static FruitShopDao fruitShopDao;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        expected = new HashMap<>();
        expected.put("banana", 20);
        expected.put("apple", 10);
    }

    @Before
    public void setUp() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 10);
    }

    @Test
    public void put_Ok() {
        fruitShopDao.put("apple", 11);
        assertEquals(11, Storage.fruits.get("apple"));
    }

    @Test
    public void get_Ok() {
        assertEquals(expected.get("banana"), fruitShopDao.get("banana"));
        assertEquals(expected.get("apple"), fruitShopDao.get("apple"));
    }

    @Test
    public void getAll_Ok() {
        assertEquals(expected, fruitShopDao.getAll());
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}

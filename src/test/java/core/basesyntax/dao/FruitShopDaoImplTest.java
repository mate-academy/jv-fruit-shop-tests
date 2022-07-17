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
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static final int PUT_VALUE = 11;
    private static FruitShopDao fruitShopDao;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        expected = new HashMap<>();
        expected.put(BANANA_KEY, 20);
        expected.put(APPLE_KEY, 10);
    }

    @Before
    public void setUp() {
        Storage.fruits.put(BANANA_KEY, 20);
        Storage.fruits.put(APPLE_KEY, 10);
    }

    @Test
    public void put_Ok() {
        fruitShopDao.put(APPLE_KEY, PUT_VALUE);
        assertEquals(PUT_VALUE, Storage.fruits.get(APPLE_KEY));
    }

    @Test
    public void get_Ok() {
        assertEquals(expected.get(BANANA_KEY), fruitShopDao.get(BANANA_KEY));
        assertEquals(expected.get(APPLE_KEY), fruitShopDao.get(APPLE_KEY));
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

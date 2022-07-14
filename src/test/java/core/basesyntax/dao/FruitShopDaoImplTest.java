package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopDaoImplTest {
    private static final String FIRST_KEY = "banana";
    private static final String SECOND_KEY = "apple";
    private static final int PUT_NUMBER = 11;
    private static FruitShopDao fruitShopDao;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        expected = new HashMap<>();
        expected.put(FIRST_KEY, 20);
        expected.put(SECOND_KEY, 10);
    }

    @Before
    public void setUp() {
        Storage.fruits.put(FIRST_KEY, 20);
        Storage.fruits.put(SECOND_KEY, 10);
    }

    @Test
    public void put_Ok() {
        assertEquals(expected.get(SECOND_KEY), Storage.fruits.get(SECOND_KEY));
        fruitShopDao.put(SECOND_KEY, PUT_NUMBER);
        assertNotEquals(expected.get(SECOND_KEY), Storage.fruits.get(SECOND_KEY));
        assertEquals(PUT_NUMBER, Storage.fruits.get(SECOND_KEY));
    }

    @Test
    public void get_Ok() {
        assertEquals(expected.get(FIRST_KEY), fruitShopDao.get(FIRST_KEY));
        assertEquals(expected.get(SECOND_KEY), fruitShopDao.get(SECOND_KEY));
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

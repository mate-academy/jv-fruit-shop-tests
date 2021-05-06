package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static final int QUANTITY = 5;
    private static FruitDao fruitDao;
    private static Map<Fruit, Integer> expectedMap;
    private static Fruit apple;

    @BeforeAll
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        expectedMap = new HashMap<>();
        apple = new Fruit("apple");
        Storage.fruits.put(apple, QUANTITY);
    }

    @Test
    public void add_addApple_isOk() {
        expectedMap.put(apple, QUANTITY);
        assertEquals(expectedMap, Storage.fruits);
    }

    @Test
    public void get_existKey_isOk() {
        int actual = fruitDao.get(apple);
        assertEquals(5, actual);
    }

    @Test
    public void get_notExistKey_isOk() {
        int actual = fruitDao.get(new Fruit("banana"));
        assertEquals(0, actual);
    }

    @Test
    public void getAll_equalsMap_isOk() {
        expectedMap.put(new Fruit("banana"), QUANTITY);
        assertNotEquals(expectedMap, Storage.fruits);
    }

    @AfterEach
    public void afterEach() {
        expectedMap.clear();
    }

    @AfterAll
    public static void afterAll() {
        Storage.fruits.clear();
    }
}

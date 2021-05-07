package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final int QUANTITY = 5;
    private static FruitDao fruitDao;
    private static Map<Fruit, Integer> expectedMap;
    private static Fruit apple;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        expectedMap = new HashMap<>();
        apple = new Fruit("apple");
        fruitDao.add(apple, QUANTITY);
    }

    @Test
    public void add_addApple_isOk() {
        expectedMap.put(apple, QUANTITY);
        assertEquals(expectedMap, fruitDao.getAll());
    }

    @Test
    public void get_existingKey_isOk() {
        int actual = fruitDao.get(apple);
        assertEquals(5, actual);
    }

    @Test
    public void get_notExistentKey_isOk() {
        int actual = fruitDao.get(new Fruit("banana"));
        assertEquals(0, actual);
    }

    @Test
    public void getAll_equalsMap_isOk() {
        expectedMap.put(new Fruit("banana"), QUANTITY);
        assertNotEquals(expectedMap,fruitDao.getAll());
    }

    @After
    public void after() {
        expectedMap.clear();
    }

    @AfterClass
    public static void afterClass() {
        fruitDao.getAll().clear();
    }
}

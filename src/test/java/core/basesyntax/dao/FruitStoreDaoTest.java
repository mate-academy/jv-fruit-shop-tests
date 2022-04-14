package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStoreDaoTest {
    private static final int QUANTITY = 5;
    private static FruitStoreDao fruitStoreDao;
    private static Map<Fruit, Integer> expectedMap;
    private static Fruit apple;

    @BeforeClass
    public static void beforeAll() {
        fruitStoreDao = new FruitStoreDaoImpl();
        expectedMap = new HashMap<>();
        apple = new Fruit("apple");
        Storage.getFruits().put(apple, QUANTITY);
    }

    @Test
    public void addFruit_addAppleToDb_returnsTrue() {
        expectedMap.put(apple, QUANTITY);
        assertEquals(expectedMap, Storage.getFruits());
    }

    @Test
    public void getQuantity_existKey_returnsTrue() {
        int actual = fruitStoreDao.getQuantity(apple);
        assertEquals(5, actual);
    }

    @Test
    public void getQuantity_notExistKey_returnsTrue() {
        int actual = fruitStoreDao.getQuantity(new Fruit("banana"));
        assertEquals(0, actual);
    }

    @Test
    public void getAll_equalsMap_returnsTrue() {
        expectedMap.put(apple, QUANTITY);
        assertEquals(expectedMap, Storage.getFruits());
    }

    @Test
    public void getAll_notEqualsMap_returnsTrue() {
        expectedMap.put(new Fruit("banana"), QUANTITY);
        assertNotEquals(expectedMap, Storage.getFruits());
    }

    @After
    public void afterEachTest() {
        expectedMap.clear();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getFruits().clear();
    }
}

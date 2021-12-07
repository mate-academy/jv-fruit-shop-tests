package core.basesyntax.db.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoStorage();
    }

    @Before
    public void setUp() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void create_FruitInStorage_OK() {
        int expected = 5;
        fruitDao.create(Fruit.APPLE, expected);
        int actual = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void create_FruitWithNullValue_Not_OK() {
        fruitDao.create(null, 10);
    }

    @Test
    public void read_getCorrectQuantityFromStorage_OK() {
        int expected = 20;
        Storage.fruitsStorage.put(Fruit.BANANA,expected);
        int actual = fruitDao.read(Fruit.BANANA);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_getQuantityFromStorageForAbsentFruit_NotOK() {
        System.out.println(fruitDao.read(Fruit.BANANA));
    }

    @Test(expected = RuntimeException.class)
    public void read_fromStorageWithNull_NotOK() {
        fruitDao.read(null);
    }

    @Test
    public void update() {
        int expected = 5;
        Storage.fruitsStorage.put(Fruit.BANANA, 10);
        fruitDao.update(Fruit.BANANA, 5);
        int actual = Storage.fruitsStorage.get(Fruit.BANANA);
        assertEquals(expected, actual);
    }
}

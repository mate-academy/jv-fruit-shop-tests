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
    public static void beforeClass() {
        fruitDao = new FruitDaoStorage();
    }

    @Before
    public void setUp() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void create_FruitWithQuantityInStorage_OK() {
        int expected = 5;
        fruitDao.create(Fruit.APPLE, expected);
        int actual = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expected, actual);
    }

    @Test
    public void read_FruitQuantityFromStorage_OK() {
        int expected = 20;
        Storage.fruitsStorage.put(Fruit.BANANA,expected);
        int actual = fruitDao.read(Fruit.BANANA);
        assertEquals(expected, actual);
    }

    @Test
    public void update_FruitQuantityInStorage() {
        int expected = 5;
        Storage.fruitsStorage.put(Fruit.BANANA, 10);
        fruitDao.update(Fruit.BANANA, expected);
        int actual = Storage.fruitsStorage.get(Fruit.BANANA);
        assertEquals(expected, actual);
    }
}

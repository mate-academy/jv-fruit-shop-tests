package shop.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.db.DataStorage;
import shop.model.Fruit;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Fruit testFruit;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        testFruit = new Fruit("apple", 1);
    }

    @Test
    public void fruitDao_getAll_ok() {
        Fruit banana = new Fruit("Banana", 10);
        Fruit orange = new Fruit("Orange", 5);
        fruitDao.add(banana);
        fruitDao.add(orange);
        Assert.assertTrue(DataStorage.storage.contains(banana));
        Assert.assertTrue(DataStorage.storage.contains(orange));
    }

    @Test
    public void fruitDao_add_ok() {
        DataStorage.storage.add(testFruit);
        Assert.assertTrue(DataStorage.storage.contains(testFruit));
    }

    @Test(expected = RuntimeException.class)
    public void fruitDao_add_null_notOk() {
        fruitDao.add(null);
    }

    @Test
    public void fruitDao_get_ok() {
        fruitDao.add(testFruit);
        assertEquals(testFruit, fruitDao.get(testFruit.getName()));
    }

    @Test
    public void fruitDao_get_null_ok() {
        assertNull(fruitDao.get(""));
    }

    @After
    public void tearDown() throws Exception {
        DataStorage.storage.clear();
    }
}

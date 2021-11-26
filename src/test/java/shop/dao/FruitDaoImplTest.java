package shop.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.db.DataStorage;
import shop.model.Fruit;

public class FruitDaoImplTest {
    private static FruitDaoImpl fruitDao;
    private static Fruit test_fruit;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        test_fruit = new Fruit("apple", 1);
    }

    @Test
    public void fruitDao_getAll_ok() {
        Fruit banana = new Fruit("Banana", 10);
        Fruit orange = new Fruit("Orange", 5);
        fruitDao.add(banana);
        fruitDao.add(orange);
        Assert.assertTrue(fruitDao.getAll().contains(banana));
        Assert.assertTrue(fruitDao.getAll().contains(orange));
    }

    @Test
    public void fruitDao_add_ok() {
        Assert.assertTrue(fruitDao.add(test_fruit));
    }

    @Test(expected = RuntimeException.class)
    public void fruitDao_add_null_notOk() {
        fruitDao.add(null);
    }

    @Test
    public void fruitDao_get_ok() {
        fruitDao.add(test_fruit);
        assertEquals(test_fruit, fruitDao.get(test_fruit.getName()));
    }

    @After
    public void tearDown() throws Exception {
        DataStorage.storage.clear();
    }
}

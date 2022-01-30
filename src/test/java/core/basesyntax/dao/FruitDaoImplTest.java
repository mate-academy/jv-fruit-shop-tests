package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void add_ok() {
        Fruit orange = new Fruit("orange");
        int quantity = 10;
        fruitDao.add(orange, quantity);
        int expected = 10;
        int actual = Storage.storage.get(orange);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_ok() {
        Fruit orange = new Fruit("orange");
        int quantity = 10;
        Storage.storage.put(orange, quantity);
        int expected = 10;
        int actual = fruitDao.getInteger(orange);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_nullKey_ok() {
        int quantity = 10;
        Storage.storage.put(null, quantity);
        int expected = 10;
        int actual = fruitDao.getInteger(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_wrongKey_notOk() {
        Fruit orange = new Fruit("orange");
        Assert.assertNull(fruitDao.getInteger(orange));
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}

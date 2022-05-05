package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitsDaoImplTest {
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void setup() {
        fruitsDao = new FruitsDaoImpl();
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }

    @Test
    public void addProduct_addNew_ok() {
        Fruit orange = new Fruit("orange");
        fruitsDao.addProduct(orange, 20);
        int expected = 20;
        int actual = Storage.storage.get(orange);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getValue_getExisted_ok() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 15);
        int expected = 15;
        int actual = fruitsDao.getValue(orange);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll_getMap_ok() {
        Fruit orange = new Fruit("orange");
        Fruit apple = new Fruit("apple");
        Fruit pear = new Fruit("pear");
        Storage.storage.put(orange, 15);
        Storage.storage.put(apple, 25);
        Storage.storage.put(pear, 35);
        Map<Fruit, Integer> expected = new HashMap<>() {
            {
                put(orange, 15);
                put(apple, 25);
                put(pear, 35);
            }
        };
        Map<Fruit, Integer> actual = fruitsDao.getAll();
        Assert.assertEquals(expected, actual);
    }
}

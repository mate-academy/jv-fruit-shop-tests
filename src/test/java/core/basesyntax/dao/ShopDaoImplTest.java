package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.shop.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopDaoImplTest {
    private static ShopDao shopDao;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        shopDao = new ShopDaoImpl();
        fruit = new Fruit("banana");
    }

    @Test
    public void addFruits_Ok() {
        boolean actual = shopDao.add(fruit, 100);
        assertTrue(actual);
    }

    @Test
    public void getFruits_Ok() {
        Storage.fruits.put(fruit, 100);
        int actual = shopDao.get(fruit);
        int expected = 100;
        assertEquals(expected, actual);
    }
}

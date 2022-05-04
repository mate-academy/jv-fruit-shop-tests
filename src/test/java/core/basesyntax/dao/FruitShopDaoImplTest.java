package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopDaoImplTest {
    private static FruitShopDao fruitShopDao;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
    }

    @Test
    public void fruitShopDao_add_isOk() {
        fruitShopDao.add("apple", 100);
        fruitShopDao.add("banana", 200);
        int expected = 2;
        int actual = Storage.fruitStorage.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fruitShopDao_getAll_isOk() {
        Storage.fruitStorage.put("apple", 100);
        Storage.fruitStorage.put("banana", 200);
        Storage.fruitStorage.put("orange", 55);
        int expected = 3;
        int actual = fruitShopDao.getAll().size();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}

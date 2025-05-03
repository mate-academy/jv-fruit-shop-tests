package core.basesyntax.dao;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopDaoImplTest {
    private static ShopDao shopDao;

    @BeforeClass
    public static void beforeClass() {
        shopDao = new ShopDaoImpl();
    }

    @Test
    public void add_ok() {
        Map<String, Integer> testStorage = new HashMap<>();
        testStorage.put("banana", 120);
        shopDao.add("banana", 120);
        Assert.assertTrue(Storage.storage.equals(testStorage));
    }

    @Test
    public void getAll_ok() {
        shopDao.add("banana", 120);
        shopDao.add("lemon", 50);
        String expect = "banana,120" + System.lineSeparator() + "lemon,50";
        String actual = shopDao.getAll();
        Assert.assertEquals(actual, expect);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}

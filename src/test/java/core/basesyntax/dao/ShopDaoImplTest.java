package core.basesyntax.dao;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopDaoImplTest {
    private static HashMap<String, Integer> testStorage;
    private static ShopDao shopDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        testStorage = new HashMap<>();
        shopDao = new ShopDaoImpl();
    }

    @Test
    public void add_ok() {
        testStorage.put("banana", 120);
        shopDao.add("banana", 120);
        Assert.assertTrue(Storage.storage.equals(testStorage));
    }

    @Test
    public void getAll_Ok() {
        shopDao.add("banana", 120);
        shopDao.add("lemon", 50);
        String expect = "banana,120" + System.lineSeparator() + "lemon,50";
        String actual = shopDao.getAll();
        Assert.assertEquals(actual, expect);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storage.clear();
    }
}

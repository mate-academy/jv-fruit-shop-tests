package core.basesyntax.dao;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopDaoImplTest {
    private static HashMap<String, Integer> testStorage;
    private static ShopDao shopDao;

    @BeforeAll
    static void beforeAll() {
        testStorage = new HashMap<>();
        shopDao = new ShopDaoImpl();
    }

    @Test
    void add_ok() {
        testStorage.put("banana", 120);
        shopDao.add("banana", 120);
        Assert.assertTrue(Storage.storage.equals(testStorage));
    }

    @Test
    void getAll_Ok() {
        shopDao.add("banana", 120);
        shopDao.add("lemon", 50);
        String expect = "banana,120" + System.lineSeparator() + "lemon,50";
        String actual = shopDao.getAll();
        Assert.assertEquals(actual, expect);
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}

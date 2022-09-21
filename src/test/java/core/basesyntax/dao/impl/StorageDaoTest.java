package core.basesyntax.dao.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoTest {
    private StorageDao storage;

    @Before
    public void before() {
        storage = new StorageDaoImpl();
    }

    @Test (expected = RuntimeException.class)
    public void get_nullProduct_NotOk() {
        storage.update(null, 1681);
    }

    @Test
    public void add_newProduct_Ok() {
        String product = "apple";
        int quantity = 1618;
        storage.update(product, quantity);
        Assert.assertTrue(Storage.storage.containsKey(product));
        Integer actual = storage.getRemainingGoods(product);
        Integer expected = 1618;
        Assert.assertEquals(expected, actual);
        Map actualMap = storage.getAllData();
        Map expectedMap = Storage.storage;
        Assert.assertEquals(expectedMap, actualMap);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}

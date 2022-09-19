package core.basesyntax.dao.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class StorageTest {
    private StorageDao storage = new StorageDaoImpl();

    @Test
    public void get_nullProduct_NotOk() {
        try {
            storage.update(null, 1681);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
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
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}

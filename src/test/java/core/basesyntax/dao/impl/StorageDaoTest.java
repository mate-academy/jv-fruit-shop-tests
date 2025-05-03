package core.basesyntax.dao.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoTest {
    public static final int QUANTITY = 1618;
    public static final String PRODUCT_NAME = "apple";
    private StorageDao storage;

    @Before
    public void before() {
        storage = new StorageDaoImpl();
    }

    @Test (expected = RuntimeException.class)
    public void get_nullProduct_NotOk() {
        storage.update(null, QUANTITY);
    }

    @Test
    public void updateStorage_Ok() {
        storage.update(PRODUCT_NAME, QUANTITY);
        Assert.assertTrue(Storage.storage.containsKey(PRODUCT_NAME));
    }

    @Test
    public void get_remainingGoods_Ok() {
        Storage.storage.put(PRODUCT_NAME, QUANTITY);
        Integer actual = storage.getRemainingGoods(PRODUCT_NAME);
        Integer expected = QUANTITY;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_AllData_Ok() {
        Storage.storage.put(PRODUCT_NAME, QUANTITY);
        Map actualMap = storage.getAllData();
        Map expectedMap = Storage.storage;
        Assert.assertEquals(expectedMap, actualMap);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}

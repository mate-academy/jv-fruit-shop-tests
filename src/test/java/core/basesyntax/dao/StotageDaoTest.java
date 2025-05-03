package core.basesyntax.dao;

import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StotageDaoTest {
    private StorageDao storageDao;
    private Map<String, Integer> expected;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        expected = new HashMap<>();
        storageDao.add("banana", 100);
        storageDao.add("apple", 150);
        storageDao.add("orange", 200);
        expected.put("banana", 100);
        expected.put("apple", 150);
        expected.put("orange", 200);
    }

    @Test
    public void equals_validDate_ok() {
        Assert.assertEquals(storageDao.getAll(), new ArrayList<>(expected.keySet()));
    }

    @Test
    public void amount_validDate_ok() {
        int expect = 100;
        int present = storageDao.getAmount("banana");
        Assert.assertEquals(expect, present);
    }

    @Test
    public void isPresent_validDate_notOk() {
        String falseFruit = "lemon";
        Assert.assertFalse(storageDao.isPresent(falseFruit));
    }

    @Test
    public void added_validDate_ok() {
        String fruit = "cherry";
        storageDao.add(fruit, 20);
        Assert.assertTrue(storageDao.isPresent(fruit));
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}

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
    private final StorageDao storageDao = new StorageDaoImpl();
    private final Map<String, Integer> expected = new HashMap<>();

    @Before
    public void setUp() {
        storageDao.add("banana", 100);
        storageDao.add("apple", 150);
        storageDao.add("orange", 200);
        expected.put("banana", 100);
        expected.put("apple", 150);
        expected.put("orange", 200);
    }

    @Test
    public void storageDao_checkedEquals() {
        Assert.assertEquals(storageDao.getAll(), new ArrayList<>(expected.keySet()));
    }

    @Test
    public void storageDao_checkedAmount_ok() {
        int expect = 100;
        int present = storageDao.getAmount("banana");
        Assert.assertEquals(expect, present);
    }

    @Test
    public void storageDao_checkedPresent_notOk() {
        String falseFruit = "lemon";
        Assert.assertFalse(storageDao.isPresent(falseFruit));
    }

    @Test
    public void storageDao_checkedAdded_ok() {
        String fruit = "cherry";
        storageDao.add(fruit, 20);
        Assert.assertTrue(storageDao.isPresent(fruit));
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}

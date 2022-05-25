package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
    }

    @After
    public void after() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void add_addDataToStorage_Ok() {
        storageDao.add("banana", 20);
        storageDao.add("apple", 10);
        Map<String, Integer> expectedDB = new HashMap<>();
        expectedDB.put("banana", 20);
        expectedDB.put("apple", 10);
        Assert.assertTrue(expectedDB.entrySet()
                .containsAll(Storage.fruitStorage.entrySet()));
        Assert.assertEquals(expectedDB.size(), Storage.fruitStorage.size());
    }

    @Test
    public void getQuantity_getQuantityFromStorage_Ok() {
        Storage.fruitStorage.put("banana", 15);
        int actual = storageDao.getQuantity("banana");
        int expected = 15;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_getQuantityByInvalidFruit_notOk() {
        Storage.fruitStorage.put("apple", 13);
        try {
            storageDao.getQuantity("banana");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void isPresent_checkIfPresentInStorage_Ok() {
        Storage.fruitStorage.put("apple", 100);
        boolean actual = storageDao.isPresent("apple");
        Assert.assertTrue(actual);
    }

    @Test
    public void isPresent_checkIfPresentInStorage_notOk() {
        Storage.fruitStorage.put("banana", 100);
        boolean actual = storageDao.isPresent("apple");
        Assert.assertFalse(actual);
    }

    @Test
    public void getAllFruits_getListOfFruitsName_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("banana");
        expected.add("apple");
        Storage.fruitStorage.put("banana", 20);
        Storage.fruitStorage.put("apple", 100);
        List<String> actual = storageDao.getAllFruits();
        Assert.assertTrue(expected.containsAll(actual));
        Assert.assertEquals(expected.size(), actual.size());
    }
}

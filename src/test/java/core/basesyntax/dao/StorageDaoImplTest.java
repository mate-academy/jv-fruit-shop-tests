package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private StorageDao storageDao = new StorageDaoImpl();

    @BeforeClass
    public static void beforeClass() {
        Storage.getStorageMap().put("banana", 25);
        Storage.getStorageMap().put("apple", 30);
    }

    @Test
    public void updateTest_Ok() {
        storageDao.update("banana",30);
        int expected = 30;
        int actual = Storage.getStorageMap().get("banana");
        assertEquals(expected,actual);

    }

    @Test
    public void getQuantity_Ok() {
        int expected = 25;
        int actual = storageDao.getQuantity("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void getStorage_Ok() {
        assertTrue(Storage.getStorageMap().containsKey("banana"));
        assertTrue(Storage.getStorageMap().containsKey("apple"));
        assertTrue(Storage.getStorageMap().containsValue(25));
        assertTrue(Storage.getStorageMap().containsValue(30));
    }
}

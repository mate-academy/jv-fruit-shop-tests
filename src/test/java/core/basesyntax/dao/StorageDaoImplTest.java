package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        Storage.fruitsStorage.put("banana",10);
        Storage.fruitsStorage.put("apple",20);
    }

    @Test
    public void update_Ok() {
        Integer expected = 20;
        storageDao.update("banana",20);
        assertEquals(expected,Storage.fruitsStorage.get("banana"));
    }

    @Test
    public void checkFruit_Ok() {
        assertTrue(storageDao.checkFruit("banana"));
        assertTrue(storageDao.checkFruit("apple"));
    }

    @Test
    public void checkFruit_NotOk() {
        assertFalse(storageDao.checkFruit("green"));
        assertFalse(storageDao.checkFruit("orange"));
    }

    @Test
    public void createFruit_Ok() {
        storageDao.createFruit("green");
        storageDao.createFruit("orange");
        assertTrue(storageDao.checkFruit("green"));
        assertTrue(storageDao.checkFruit("orange"));
    }

    @Test
    public void getCountFruit_Ok() {
        Integer expectedBanana = 10;
        Integer expectedApple = 20;
        assertEquals(expectedBanana,storageDao.getCountFruit("banana"));
        assertEquals(expectedApple,storageDao.getCountFruit("apple"));
    }

    @Test
    public void getCountFruitUnknown_NotOk() {
        Integer expected = 0;
        assertEquals(expected,storageDao.getCountFruit("green"));
    }

    @Test
    public void getAllFruitsFromStorage_Ok() {
        assertEquals(2,storageDao.getAllFruitsFromStorage().size());
        storageDao.createFruit("green");
        assertEquals(3,storageDao.getAllFruitsFromStorage().size());
        storageDao.createFruit("orange");
        assertEquals(4,storageDao.getAllFruitsFromStorage().size());
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitsStorage.clear();
    }
}

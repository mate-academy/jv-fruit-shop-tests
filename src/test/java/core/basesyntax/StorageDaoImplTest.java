package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;
    private static Map<String, Integer> storageTest;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        storageTest = new HashMap<>();
    }

    @Before
    public void setUp() {
        storageTest.put("peach", 50);
        storageTest.put("banana", 100);
        storageTest.put("apple", 20);
        storageDao.update("peach", 50);
        storageDao.update("banana", 100);
        storageDao.update("apple", 20);
    }

    @Test
    public void update_Ok() {
        Assert.assertEquals(storageTest, Storage.storageMap);
    }

    @Test
    public void remainder_Ok() {
        Assert.assertEquals(50, storageDao.remainder("peach"));
        Assert.assertEquals(100, storageDao.remainder("banana"));
        Assert.assertEquals(20, storageDao.remainder("apple"));
    }

    @Test
    public void getStorage_Ok() {
        Assert.assertEquals(storageTest, storageDao.getStorage());
    }

    @After
    public void afterEachTest() {
        Storage.storageMap.clear();
        storageTest.clear();
    }
}

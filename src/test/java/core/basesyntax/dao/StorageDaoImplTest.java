package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QTY = 10;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void add_ok() {
        storageDao.add(TEST_FRUIT, TEST_QTY);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
        assertTrue(Storage.fruits.containsValue(TEST_QTY));
    }

    @Test
    public void get_ok() {
        Storage.fruits.put(TEST_FRUIT, TEST_QTY);
        assertEquals(TEST_QTY, (int) storageDao.get(TEST_FRUIT));
    }

    @Test
    public void get_notExistingFruit_ok() {
        Storage.fruits.put(TEST_FRUIT, TEST_QTY);
        assertNull(storageDao.get("banana"));
    }

    @Test
    public void getAll() {
        Storage.fruits.put(TEST_FRUIT, TEST_QTY);
        Storage.fruits.put(TEST_FRUIT + "2", TEST_QTY + 2);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(TEST_FRUIT, TEST_QTY);
        expected.put(TEST_FRUIT + "2", TEST_QTY + 2);
        assertEquals(expected, storageDao.getAll());
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}

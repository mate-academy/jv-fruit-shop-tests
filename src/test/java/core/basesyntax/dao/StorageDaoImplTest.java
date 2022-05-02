package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUpFirst() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void add_correctValue_Ok() {
        assertTrue(storageDao.add(new Fruit("apple"), 0));
    }

    @Test(expected = RuntimeException.class)
    public void add_nullFruit_NotOk() {
        storageDao.add(null, 0);
    }

    @Test(expected = RuntimeException.class)
    public void add_invalidQuantity_NotOk() {
        storageDao.add(new Fruit("orange"), -1);
    }

    @Test
    public void get_correctValue_Ok() {
        Fruit orange = new Fruit("orange");
        storageDao.add(orange, 0);
        assertEquals(Integer.valueOf(0), storageDao.get(orange));
    }

    @Test
    public void getAll_emptyStorage_Ok() {
        assertTrue(storageDao.getAll().isEmpty());
    }

    @Test
    public void get_invalidValue_NotOk() {
        assertNull(storageDao.get(new Fruit("apple")));
    }

    @After
    public void clearStorage() {
        storageDao.clear();
    }
}

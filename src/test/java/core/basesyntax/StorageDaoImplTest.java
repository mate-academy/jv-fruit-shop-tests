package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private StorageDao storageDao;

    @Before
    public void setup() {
        storageDao = new StorageDaoImpl();
    }

    @After
    public void close() {
        Storage.storage.clear();
    }

    @Test
    public void saveGet_ok() {
        for (int i = 0; i < 10; i++) {
            storageDao.save("Fruit " + i, i);
        }
        assertTrue(storageDao.getAll().size() == 10);
        for (int i = 0; i < 10; i++) {
            assertEquals(Integer.valueOf(i), Storage.storage.get("Fruit " + i));
        }
        Storage.storage.clear();
        for (int i = 0; i < 100; i++) {
            storageDao.save("Fruit " + i, i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(Integer.valueOf(i), storageDao.getQuantity("Fruit " + i));
        }
    }

}

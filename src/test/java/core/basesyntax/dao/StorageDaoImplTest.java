package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        storageDao.add("banana", 100);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void add_storageIsEmpty_NotOk() {
        assertEquals(1, Storage.storage.size());
    }

    @Test
    void get_storageFruitAmountEqualsToAmountForThisFruit() {
        assertEquals(100, storageDao.get("banana"));
    }

    @Test
    void get_noSuchFruitInStorage_Ok() {
        assertEquals(0, storageDao.get("apple"));
    }

}

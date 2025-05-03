package core.basesyntax.dao.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private final Fruit banana = new Fruit("banana");
    private final Fruit apple = new Fruit("apple");
    private final int quantity = 50;

    @AfterEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void addProductToEmptyStorage_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(banana, quantity);
        Integer expected = 50;
        Integer actual = Storage.storage.get(banana);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void subtractProduct_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(banana,60);
        storageDao.substractAmount(banana, quantity);
        Integer expected = 10;
        Integer actual = Storage.storage.get(banana);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void substractMoreThanStorageHave_NotOk() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(banana,10);
        Assert.assertThrows(RuntimeException.class,
                () -> storageDao.substractAmount(banana,quantity));
    }

    @Test
    void getAllProduct_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(banana, 10);
        storageDao.add(apple, 20);
        storageDao.getAll();
        Map<Fruit, Integer> expected = new HashMap<>(Map.of(
                banana, 10, apple, 20));
        Assertions.assertEquals(expected, storageDao.getAll());
    }
}



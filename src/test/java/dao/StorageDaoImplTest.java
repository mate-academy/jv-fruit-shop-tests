package dao;

import db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeAll
    static void init() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void fillStorage() {
        storageDao.addFruitToStorage("First", 10);
        storageDao.addFruitToStorage("Second", 20);
        storageDao.addFruitToStorage("Third", 30);
    }

    @Test
    void addFruitToStorage_correctValues_ok() {
        Assertions.assertEquals(Storage.STORAGE.size(), 3);
        storageDao.addFruitToStorage("Apple", 10);
        Assertions.assertEquals(Storage.STORAGE.size(), 4);
        storageDao.addFruitToStorage("Orange", 123);
        Assertions.assertEquals(Storage.STORAGE.size(),5);
    }

    @Test
    void getAmount_correctValues_ok() {
        Assertions.assertEquals(storageDao.getAmount("First"), 10);
        Assertions.assertEquals(storageDao.getAmount("Second"), 20);
    }

    @Test
    void getAmount_incorrectValue_ok() {
        Assertions.assertEquals(storageDao.getAmount("Forth"), 0);
    }

    @Test
    void getAll_ok() {
        Map<String, Integer> expected = Map.of("First", 10, "Second",20, "Third",30);
        Assertions.assertEquals(storageDao.getAll(), expected);
    }

    @AfterEach
    void clearStorage() {
        Storage.STORAGE.clear();
    }
}

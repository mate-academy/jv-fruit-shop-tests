package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void add() {
        Map<String, Integer> expected = Map.of("banana", 23);
        storageDao.add("banana", 23);
        assertEquals(expected, Storage.fruits);
    }

    @Test
    void getQuantity() {
        Map<String, Integer> testStorage = Map.of("banana", 23);
        storageDao.add("banana", 23);
        int expected = testStorage.get("banana");
        int actual = storageDao.getQuantity("banana");
        assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        final Map<String, Integer> expected = Map.of("banana", 23,
                "apple", 52,
                "mango", 13,
                "kiwi", 15);
        storageDao.add("banana", 23);
        storageDao.add("apple", 52);
        storageDao.add("mango", 13);
        storageDao.add("kiwi", 15);
        Map<String, Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        final Map<String, Integer> expected = Map.of("banana", 23,
                "apple", 52,
                "mango", 999,
                "kiwi", 15);
        storageDao.add("banana", 23);
        storageDao.add("apple", 52);
        storageDao.add("mango", 13);
        storageDao.add("kiwi", 15);
        storageDao.update("mango", 999);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}

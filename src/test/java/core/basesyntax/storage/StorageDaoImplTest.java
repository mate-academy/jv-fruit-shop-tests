package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        Storage.setFruitStorage(new HashMap<>());
        storageDao = new StorageDaoImpl();
    }

    @Test
    void testAddNewFruit() {
        storageDao.add("apple", 10);
        assertEquals(10, Storage.getFruitStorage().get("apple"));
    }

    @Test
    void testAddExistingFruit() {
        storageDao.add("apple", 10);
        storageDao.add("apple", 5);
        assertEquals(15, Storage.getFruitStorage().get("apple"));
    }

    @Test
    void testRemoveFruit() {
        storageDao.add("apple", 10);
        storageDao.remove("apple", 5);
        assertEquals(5, Storage.getFruitStorage().get("apple"));
    }

    @Test
    void testRemoveFruitNotInStorage() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storageDao.remove("banana", 5);
        });
        assertEquals("There's no banana in storage.", exception.getMessage());
    }

    @Test
    void testRemoveTooMuchFruit() {
        storageDao.add("apple", 10);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storageDao.remove("apple", 15);
        });
        assertEquals("apple quantity can't be -5", exception.getMessage());
    }
}

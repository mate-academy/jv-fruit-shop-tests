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
        storageDao.add("Apple", 10);
        assertEquals(10, Storage.getFruitStorage().get("Apple"));
    }

    @Test
    void testAddExistingFruit() {
        storageDao.add("Apple", 10);
        storageDao.add("Apple", 5);
        assertEquals(15, Storage.getFruitStorage().get("Apple"));
    }

    @Test
    void testRemoveFruit() {
        storageDao.add("Apple", 10);
        storageDao.remove("Apple", 5);
        assertEquals(5, Storage.getFruitStorage().get("Apple"));
    }

    @Test
    void testRemoveFruitNotInStorage() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storageDao.remove("Banana", 5);
        });
        assertEquals("There's no Banana in storage.", exception.getMessage());
    }

    @Test
    void testRemoveTooMuchFruit() {
        storageDao.add("Apple", 10);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storageDao.remove("Apple", 15);
        });
        assertEquals("Apple quantity can't be -5", exception.getMessage());
    }
}

package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageImplTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
    }

    @AfterEach
    void tearDown() {
        StorageImpl.fruitStorage.clear();
    }

    @Test
    void updateFruitBalance_Ok() {
        storage.updateFruitBalance("apple", 1);
        assertEquals(1,StorageImpl.fruitStorage.get("apple"));
    }

    @Test
    void updateFruitBalance_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> storage.updateFruitBalance("apple", -1));
        assertEquals("Balance of fruits can't be negative -1", exception.getMessage());
    }

    @Test
    void getFruitBalanceFromEmptyStorage_Ok() {
        assertEquals(0,storage.getFruitBalance("apple"));
    }

    @Test
    void getFruitBalanceFromStorage_Ok() {
        storage.setFruitBalance("apple", 40);
        assertEquals(40, StorageImpl.fruitStorage.get("apple"));
    }

    @Test
    void setFruitBalance_Ok() {
        storage.setFruitBalance("apple", 1);
        assertEquals(1,StorageImpl.fruitStorage.get("apple"));
    }

    @Test
    void setFruitBalance_NotOk() {
        assertThrows(RuntimeException.class,
                () -> storage.setFruitBalance("apple", -1));
    }

    @Test
    void getAllFromNonEmptyStorage_Ok() {
        StorageImpl.fruitStorage.put("apple", 10);
        StorageImpl.fruitStorage.put("banana", 20);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 10);
        expected.put("banana", 20);
        assertEquals(expected, storage.getAll());
    }

    @Test
    void getAllFromEmptyStorage_Ok() {
        assertEquals(new HashMap<String,Integer>(), storage.getAll());
    }

    @Test
    void clearAll_Ok() {
        StorageImpl.fruitStorage.put("apple",100);
        storage.clearStorage();
        assertEquals(0, StorageImpl.fruitStorage.size());
    }
}

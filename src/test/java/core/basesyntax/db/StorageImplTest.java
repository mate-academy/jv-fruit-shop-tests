package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageImplTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
        storage.clearStorage();
    }

    @Test
    void updateFruitBalance_Ok() {
        storage.updateFruitBalance("apple", 1);
        assertEquals(1,storage.getFruitBalance("apple"));
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
        assertEquals(40, storage.getFruitBalance("apple"));
    }

    @Test
    void setFruitBalance_Ok() {
        storage.setFruitBalance("apple", 1);
        assertEquals(1,storage.getFruitBalance("apple"));
    }

    @Test
    void setFruitBalance_NotOk() {
        assertThrows(RuntimeException.class,
                () -> storage.setFruitBalance("apple", -1));
    }

    @Test
    void getAllFromNonEmptyStorage_Ok() {
        storage.updateFruitBalance("apple", 10);
        storage.updateFruitBalance("banana", 20);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 10);
        expected.put("banana", 20);
        assertEquals(expected, storage.getAll());
    }

    @Test
    void getAllFromEmptyStorage_Ok() {
        assertEquals(new HashMap<String,Integer>(),storage.getAll());
    }

    @Test
    void clearAll_Ok() {
        storage.setFruitBalance("apple",100);
        storage.setFruitBalance("orange",10);
        storage.clearStorage();
        assertEquals(new HashMap<String,Integer>(), storage.getAll());
    }

    @Test
    void clearStorage_AfterMultipleUpdates_Ok() {
        storage.updateFruitBalance("apple", 10);
        storage.updateFruitBalance("banana", 20);
        storage.clearStorage();
        assertEquals(new HashMap<String, Integer>(), storage.getAll());
    }
}

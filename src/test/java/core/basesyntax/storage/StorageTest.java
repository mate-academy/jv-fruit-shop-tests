package core.basesyntax.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void putGetIncreaseDecrease() {
        storage.put("apple", 10);
        assertEquals(10, storage.get("apple"));
        storage.increase("apple", 5);
        assertEquals(15, storage.get("apple"));
        storage.decrease("apple", 3);
        assertEquals(12, storage.get("apple"));
    }

    @Test
    void getDefaultZero() {
        assertEquals(0, storage.get("nonexistent"));
    }

    @Test
    void getAllShouldReturnUnmodifiableMap() {
        storage.put("a", 1);
        Map<String, Integer> all = storage.getAll();
        assertTrue(all.containsKey("a"));
        assertThrows(UnsupportedOperationException.class, () -> all.put("b", 2));
    }

    @Test
    void nullFruitShouldThrow() {
        assertThrows(FruitShopException.class, () -> storage.get(null));
        assertThrows(FruitShopException.class, () -> storage.put(null, 1));
    }
}

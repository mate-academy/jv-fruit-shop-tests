package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void addFruit_addNewFruitsMoreThanZero_ok() {
        Storage.addFruit(APPLE, 10);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertEquals(10, storage.get(APPLE));
    }

    @Test
    void addFruit_addOneFruit_ok() {
        Storage.addFruit(APPLE, 1);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertEquals(1, storage.get(APPLE));
    }

    @Test
    void addFruit_addZeroFruits_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Storage.addFruit(APPLE, 0));
    }

    @Test
    void addFruit_addFruitsLessThanZero_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Storage.addFruit(APPLE, -1));
    }

    @Test
    void addFruit_nullFruitValue_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Storage.addFruit(null, 5));
    }

    @Test
    void addFruit_emptyFruitValue_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Storage.addFruit("", 5));
    }

    @Test
    void addFruit_addExistingFruits_ok() {
        Storage.addFruit(BANANA, 5);
        Storage.addFruit(BANANA, 15);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(BANANA));
        assertEquals(20, storage.get(BANANA));
    }

    @Test
    void removeFruit_removeFruitsMoreThanZero_ok() {
        Storage.addFruit(APPLE, 10);
        Storage.removeFruit(APPLE, 5);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertEquals(5, storage.get(APPLE));
    }

    @Test
    void removeFruit_removeLastFruit_ok() {
        Storage.addFruit(BANANA, 1);
        Storage.removeFruit(BANANA, 1);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(BANANA));
        assertEquals(0, storage.get(BANANA));
    }

    @Test
    void removeFruit_removeFruitsFromEmptyStorage_notOk() {
        Storage.addFruit(APPLE, 10);
        Storage.removeFruit(APPLE, 10);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertThrows(RuntimeException.class,
                () -> Storage.removeFruit(APPLE, 2));
    }

    @Test
    void removeFruit_removeZeroFruits_notOk() {
        Storage.addFruit(APPLE, 10);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertThrows(IllegalArgumentException.class,
                () -> Storage.removeFruit(APPLE, 0));
    }

    @Test
    void removeFruit_removeFruitsLessThanZero_notOk() {
        Storage.addFruit(APPLE, 10);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertThrows(IllegalArgumentException.class,
                () -> Storage.removeFruit(APPLE, -1));
    }

    @Test
    void removeFruit_removeNonExistentFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> Storage.removeFruit(APPLE, 5));
    }

    @Test
    void removeFruit_nullFruitValue_notOk() {
        Storage.addFruit(APPLE, 10);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertThrows(IllegalArgumentException.class,
                () -> Storage.removeFruit(null, 5));
    }

    @Test
    void removeFruit_emptyFruitValue_notOk() {
        Storage.addFruit(APPLE, 10);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertThrows(IllegalArgumentException.class,
                () -> Storage.removeFruit("", 5));
    }
}

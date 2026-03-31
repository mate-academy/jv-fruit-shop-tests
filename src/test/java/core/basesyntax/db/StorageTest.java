package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
    }

    @BeforeEach
    void clearStorage() {
        storage.clear();
    }

    @Test
    void setOK() {
        storage.set("apple", 10);
        assertEquals(10, storage.getQuantityByFruit("apple"));
    }

    @Test
    void setZeroQuantity() {
        storage.set("apple", 0);
        assertEquals(0, storage.getQuantityByFruit("apple"));
    }

    @Test
    void add() {
        storage.add("grape", 12);
        assertEquals(12, storage.getQuantityByFruit("grape"));
    }

    @Test
    void addZero() {
        storage.add("grape", 0);
        assertEquals(0, storage.getQuantityByFruit("grape"));
    }

    @Test
    void subtract() {
        storage.set("apple", 12);
        storage.subtract("apple", 5);
        assertEquals(12 - 5, storage.getQuantityByFruit("apple"));
    }

    @Test
    void subtractZeroFromZero() {
        storage.set("apple", 0);
        storage.subtract("apple", 0);
        assertEquals(0, storage.getQuantityByFruit("apple"));
    }

    @Test
    void getAll() {
        Map<String, Integer> fruitMap = new HashMap<>();
        fruitMap.put("apple", 12);
        fruitMap.put("grape", 17);

        for (Map.Entry<String, Integer> entry : fruitMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            storage.set(key, value);
        }

        assertEquals(storage.getAll(), fruitMap);
    }

    @Test
    void getAllFromEmptyMap() {
        Map<String, Integer> fruitMap = new HashMap<>();
        assertEquals(storage.getAll(), fruitMap);
    }
}

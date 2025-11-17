package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    @BeforeEach
    void setUp() {
        Storage.put("apple", null);
        Storage.put("banana", null);
        Storage.put("x", null);
    }

    @Test
    void put_getOrDefault() {
        Storage.put("apple", 5);
        assertEquals(Integer.valueOf(5), Storage.getOrDefault("apple", 0));
    }

    @Test
    void getOrDefault_unknownKey_returnsDefault() {
        assertEquals(Integer.valueOf(0), Storage.getOrDefault("unknown", 0));
    }

    @Test
    void getFruitsTest() {
        Storage.put("apple", 10);
        Storage.put("banana", 20);

        Map<String, Integer> copy = Storage.getFruits();

        copy.put("apple", 999);
        copy.put("newFruit", 777);

        Map<String, Integer> fresh = Storage.getFruits();

        assertEquals(Integer.valueOf(10), fresh.get("apple"));
        assertEquals(Integer.valueOf(20), fresh.get("banana"));
        assertFalse(fresh.containsKey("newFruit"));
    }
}

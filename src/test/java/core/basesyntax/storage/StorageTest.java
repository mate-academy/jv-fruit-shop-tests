package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Map<String, Integer> testMap;

    @BeforeEach
    void setUp() {
        testMap = new HashMap<>();
        testMap.put("apple", 10);
        testMap.put("banana", 20);
    }

    @Test
    void testSetFruitStorage() {
        Storage.setFruitStorage(testMap);
        assertEquals(10, Storage.getFruitStorage().get("apple"));
        assertEquals(20, Storage.getFruitStorage().get("banana"));
    }

    @Test
    void testGetFruitStorage_AfterSet() {
        Storage.setFruitStorage(testMap);
        Map<String, Integer> retrievedStorage = Storage.getFruitStorage();
        assertNotNull(retrievedStorage);
        assertEquals(2, retrievedStorage.size());
        assertTrue(retrievedStorage.containsKey("apple"));
        assertTrue(retrievedStorage.containsKey("banana"));
    }

    @Test
    void testSetFruitStorage_UpdateStorage() {
        Storage.setFruitStorage(testMap);
        Map<String, Integer> newTestMap = new HashMap<>();
        newTestMap.put("orange", 30);
        Storage.setFruitStorage(newTestMap);
        assertEquals(1, Storage.getFruitStorage().size());
        assertTrue(Storage.getFruitStorage().containsKey("orange"));
        assertFalse(Storage.getFruitStorage().containsKey("apple"));
    }
}

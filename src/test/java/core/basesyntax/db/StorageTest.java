package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void storageGenerate_Ok() {
        Storage.fruitStorage.put("banana", 20);
        assertEquals(20, Storage.fruitStorage.get("banana"));
    }

    @Test
    void storageUpdateFruitQuantity() {
        Storage.fruitStorage.put("apple", 15);
        Storage.fruitStorage.put("apple", 20);
        assertEquals(20, Storage.fruitStorage.get("apple"));
    }

    @Test
    void storageRemoveFruit_Ok() {
        Storage.fruitStorage.put("apple", 20);
        Storage.fruitStorage.remove("apple");
        assertFalse(Storage.fruitStorage.containsKey("apple"));
    }

    @Test
    void storageInitiallyEmpty_Ok() {
        assertTrue(Storage.fruitStorage.isEmpty());
    }

    @Test
    void storageMultipleFruits_Ok() {
        Storage.fruitStorage.put("apple", 20);
        Storage.fruitStorage.put("banana", 15);
        Storage.fruitStorage.put("cherry", 25);
        assertEquals(3, Storage.fruitStorage.size());
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}

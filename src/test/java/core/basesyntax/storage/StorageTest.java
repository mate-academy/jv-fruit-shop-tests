package core.basesyntax.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
    }

    @Test
    void putFruit_and_getQuantity_returnsCorrectValue() {
        Storage.putFruit("banana", 50);
        assertEquals(50, Storage.getQuantity("banana"));
    }

    @Test
    void getQuantity_fruitNotPresent_returnsZero() {
        assertEquals(0, Storage.getQuantity("apple"));
    }

    @Test
    void containsFruit_fruitExists_returnsTrue() {
        Storage.putFruit("apple", 10);
        assertTrue(Storage.containsFruit("apple"));
    }

    @Test
    void containsFruit_fruitNotExists_returnsFalse() {
        assertFalse(Storage.containsFruit("mango"));
    }

    @Test
    void getAllFruits_returnsCopyOfStorage() {
        Storage.putFruit("apple", 10);
        Map<String, Integer> fruits = Storage.getAllFruits();
        fruits.put("banana", 20); // Should not affect the internal map

        assertFalse(Storage.containsFruit("banana"));
    }

    @Test
    void clearStorage_removesAllFruits() {
        Storage.putFruit("apple", 10);
        Storage.clearStorage();
        assertTrue(Storage.getAllFruits().isEmpty());
    }
}

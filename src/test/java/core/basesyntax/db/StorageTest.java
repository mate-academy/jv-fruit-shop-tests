package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.getAllFruits().forEach((fruit, quantity) -> {
            try {
                Storage.removeFruit(fruit, quantity);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void addFruit_shouldAddFruitToStorage() {
        Storage.addFruit("Apple", 10);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(10, fruits.get("Apple"), "Expected 10 apples in storage");

        Storage.addFruit("Apple", 5);
        fruits = Storage.getAllFruits();
        assertEquals(15, fruits.get("Apple"), "Expected 15 apples in storage after adding 5 more");
    }

    @Test
    void removeFruit_shouldDecreaseFruitQuantity() {
        Storage.addFruit("banana", 20);
        Storage.removeFruit("banana",5);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(15, (int) fruits.get("banana"),
                "The quantity of bananas should be 15 after removing");
    }

    @Test
    void testRemoveFruitThrowsExceptionWhenNotEnough() {
        Storage.addFruit("orange", 10);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Storage.removeFruit("orange", 15);
        });
        assertEquals("Not enough orange in storage", exception.getMessage());
    }

    @Test
    void testSetFruitShouldOverrideQuantity() {
        Storage.addFruit("apple", 10);
        Storage.setFruit("apple", 50);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(50, (int) fruits.get("apple"),
                "The quantity of apples should be 50 after setting it");
    }

    @Test
    void testClearShouldRemoveAllFruits() {
        Storage.addFruit("apple", 10);
        Storage.addFruit("banana", 20);
        Storage.clear();
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertTrue(fruits.isEmpty(), "Storage should be empty after calling clear");
    }

    @Test
    void testGetAllFruitsReturnsCorrectQuantities() {
        Storage.addFruit("apple", 10);
        Storage.addFruit("banana", 20);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(10, (int) fruits.get("apple"), "The quantity of apples should be 10");
        assertEquals(20, (int) fruits.get("banana"), "The quantity of bananas should be 20");
    }
}



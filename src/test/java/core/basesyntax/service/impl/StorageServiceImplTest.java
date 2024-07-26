package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.StorageService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        storageService.clear();
    }

    @Test
    void addFruit_validInput_shouldAddFruit() {
        storageService.addFruit("apple", 10);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(10, fruits.get("apple"));
    }

    @Test
    void addFruit_negativeQuantity_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> storageService.addFruit("apple", -10));
    }

    @Test
    void addFruit_nullName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> storageService.addFruit(null, 10));
    }

    @Test
    void addFruit_emptyName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> storageService.addFruit("", 10));
    }

    @Test
    void removeFruit_validInput_shouldRemoveFruit() {
        storageService.addFruit("apple", 40);
        storageService.removeFruit("apple", 35);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(5, fruits.get("apple"));
    }

    @Test
    void removeFruit_insufficientQuantity_shouldThrowException() {
        storageService.addFruit("apple", 10);
        assertThrows(IllegalArgumentException.class, () -> storageService.removeFruit("apple", 15));
    }

    @Test
    void removeFruit_nullName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> storageService.removeFruit(null, 10));
    }

    @Test
    void removeFruit_emptyName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> storageService.removeFruit("", 10));
    }

    @Test
    void updateFruitQuantity_validInput_shouldSetQuantity() {
        storageService.addFruit("apple", 40);
        storageService.updateFruitQuantity("apple", 100);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(100, fruits.get("apple"));
    }

    @Test
    void updateFruitQuantity_negativeQuantity_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.updateFruitQuantity("apple", -10));
    }

    @Test
    void updateFruitQuantity_nullName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.updateFruitQuantity(null, 10));
    }

    @Test
    void updateFruitQuantity_emptyName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.updateFruitQuantity("", 10));
    }

    @Test
    void getAllFruits_shouldReturnAllFruits() {
        storageService.addFruit("apple", 100);
        storageService.addFruit("banana", 50);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(Map.of("apple", 100, "banana", 50), fruits);
    }

    @Test
    void clear_shouldRemoveAllFruits() {
        storageService.addFruit("apple", 100);
        storageService.addFruit("banana", 50);
        storageService.clear();
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertTrue(fruits.isEmpty());
    }
}

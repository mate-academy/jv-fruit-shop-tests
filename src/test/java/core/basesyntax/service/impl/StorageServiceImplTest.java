package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.StorageService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_40 = 40;
    private static final int QUANTITY_35 = 35;
    private static final int QUANTITY_100 = 100;
    private static final int QUANTITY_50 = 50;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final String EMPTY_NAME = "";
    private static final String NULL_NAME = null;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        storageService.clear();
    }

    @Test
    void addFruit_validInput_shouldAddFruit() {
        storageService.addFruit(APPLE, QUANTITY_10);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(QUANTITY_10, fruits.get(APPLE));
    }

    @Test
    void addFruit_negativeQuantity_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.addFruit(APPLE, NEGATIVE_QUANTITY));
    }

    @Test
    void addFruit_nullName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.addFruit(NULL_NAME, QUANTITY_10));
    }

    @Test
    void addFruit_emptyName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.addFruit(EMPTY_NAME, QUANTITY_10));
    }

    @Test
    void removeFruit_validInput_shouldRemoveFruit() {
        storageService.addFruit(APPLE, QUANTITY_40);
        storageService.removeFruit(APPLE, QUANTITY_35);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(QUANTITY_40 - QUANTITY_35, fruits.get(APPLE));
    }

    @Test
    void removeFruit_insufficientQuantity_shouldThrowException() {
        storageService.addFruit(APPLE, QUANTITY_10);
        assertThrows(IllegalArgumentException.class,
                () -> storageService.removeFruit(APPLE, QUANTITY_10 + 5));
    }

    @Test
    void removeFruit_nullName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.removeFruit(NULL_NAME, QUANTITY_10));
    }

    @Test
    void removeFruit_emptyName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.removeFruit(EMPTY_NAME, QUANTITY_10));
    }

    @Test
    void updateFruitQuantity_validInput_shouldSetQuantity() {
        storageService.addFruit(APPLE, QUANTITY_40);
        storageService.updateFruitQuantity(APPLE, QUANTITY_100);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(QUANTITY_100, fruits.get(APPLE));
    }

    @Test
    void updateFruitQuantity_negativeQuantity_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.updateFruitQuantity(APPLE, NEGATIVE_QUANTITY));
    }

    @Test
    void updateFruitQuantity_nullName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.updateFruitQuantity(NULL_NAME, QUANTITY_10));
    }

    @Test
    void updateFruitQuantity_emptyName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> storageService.updateFruitQuantity(EMPTY_NAME, QUANTITY_10));
    }

    @Test
    void getAllFruits_shouldReturnAllFruits() {
        storageService.addFruit(APPLE, QUANTITY_100);
        storageService.addFruit(BANANA, QUANTITY_50);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(Map.of(APPLE, QUANTITY_100, BANANA, QUANTITY_50), fruits);
    }

    @Test
    void clear_shouldRemoveAllFruits() {
        storageService.addFruit(APPLE, QUANTITY_100);
        storageService.addFruit(BANANA, QUANTITY_50);
        storageService.clear();
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertTrue(fruits.isEmpty());
    }
}


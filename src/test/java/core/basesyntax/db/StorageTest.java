package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final int INITIAL_APPLE_QUANTITY = 10;
    private static final int UPDATED_APPLE_QUANTITY = 50;
    private static final int INITIAL_BANANA_QUANTITY = 20;
    private static final int REMOVE_BANANA_QUANTITY = 5;
    private static final int ORANGE_QUANTITY = 10;
    private static final int REMOVE_ORANGE_QUANTITY = 15;

    @BeforeEach
    void setUp() {
        Storage.getAllFruits().forEach(Storage::removeFruit);
    }

    @Test
    void addFruit_shouldAddFruitToStorage() {
        Storage.addFruit(APPLE, INITIAL_APPLE_QUANTITY);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(INITIAL_APPLE_QUANTITY, fruits.get(APPLE),
                "Expected " + INITIAL_APPLE_QUANTITY + " apples in storage");
    }

    @Test
    void removeFruit_shouldDecreaseFruitQuantity() {
        Storage.addFruit(BANANA, INITIAL_BANANA_QUANTITY);
        Storage.removeFruit(BANANA,REMOVE_BANANA_QUANTITY);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(Integer.valueOf(INITIAL_BANANA_QUANTITY - REMOVE_BANANA_QUANTITY),
                fruits.get(BANANA),
                "The quantity of bananas should be "
                        + (INITIAL_BANANA_QUANTITY - REMOVE_BANANA_QUANTITY) + " after removing");
    }

    @Test
    void testRemoveFruitThrowsExceptionWhenNotEnough() {
        Storage.addFruit(ORANGE, ORANGE_QUANTITY);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Storage.removeFruit(ORANGE, REMOVE_ORANGE_QUANTITY);
        });
        assertEquals("Not enough " + ORANGE + " in storage", exception.getMessage());
    }

    @Test
    void testSetFruitShouldOverrideQuantity() {
        Storage.addFruit(APPLE, INITIAL_APPLE_QUANTITY);
        Storage.setFruit(APPLE, UPDATED_APPLE_QUANTITY);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(UPDATED_APPLE_QUANTITY, fruits.get(APPLE),
                "The quantity of apples should be " + UPDATED_APPLE_QUANTITY + " after setting it");
    }

    @Test
    void testClearShouldRemoveAllFruits() {
        Storage.addFruit(APPLE, INITIAL_APPLE_QUANTITY);
        Storage.addFruit(BANANA, INITIAL_BANANA_QUANTITY);
        Storage.clear();
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertTrue(fruits.isEmpty(), "Storage should be empty after calling clear");
    }

    @Test
    void testGetAllFruitsReturnsCorrectQuantities() {
        Storage.addFruit(APPLE, INITIAL_APPLE_QUANTITY);
        Storage.addFruit(BANANA, INITIAL_BANANA_QUANTITY);
        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(INITIAL_APPLE_QUANTITY, fruits.get(APPLE),
                "The quantity of apples should be " + INITIAL_APPLE_QUANTITY);
        assertEquals(INITIAL_BANANA_QUANTITY, fruits.get(BANANA),
                "The quantity of bananas should be " + INITIAL_BANANA_QUANTITY);
    }
}



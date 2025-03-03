package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubtractOperationHandlerTest {
    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void apply_shouldSubtractFromExistingFruit() {
        Storage.inventory.put("apple", 10);
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, "apple", 3);
        assertEquals(7, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldResultInNegative_whenFruitNotPresent() {
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, "banana", 5);
        assertEquals(-5, Storage.inventory.get("banana"));
    }

    @Test
    void apply_shouldNotChangeValue_whenZeroQuantity() {
        Storage.inventory.put("apple", 10);
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, "apple", 0);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldIncreaseValue_whenNegativeQuantity() {
        Storage.inventory.put("apple", 10);
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, "apple", -4);
        assertEquals(14, Storage.inventory.get("apple"));
    }

    @Test
    void apply_multipleOperationsShouldAccumulateSubtraction() {
        Storage.inventory.put("orange", 20);
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, "orange", 5);
        handler.apply(Storage.inventory, "orange", 3);
        assertEquals(12, Storage.inventory.get("orange"));
    }

    @Test
    void apply_shouldWorkWithMultipleFruits() {
        Storage.inventory.put("apple", 15);
        Storage.inventory.put("banana", 10);
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, "apple", 5);
        handler.apply(Storage.inventory, "banana", 4);
        assertEquals(10, Storage.inventory.get("apple"));
        assertEquals(6, Storage.inventory.get("banana"));
    }

    @Test
    void apply_shouldWorkWithNullFruitKey() {
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, null, 5);
        assertEquals(-5, Storage.inventory.get(null));
        handler.apply(Storage.inventory, null, -3);
        assertEquals(-2, Storage.inventory.get(null));
    }

    @Test
    void apply_shouldHandleLargeQuantitySubtraction() {
        Storage.inventory.put("mango", 1000);
        SubtractOperationHandler handler = new SubtractOperationHandler();
        handler.apply(Storage.inventory, "mango", 500);
        assertEquals(500, Storage.inventory.get("mango"));
        handler.apply(Storage.inventory, "mango", 600);
        assertEquals(-100, Storage.inventory.get("mango"));
    }
}

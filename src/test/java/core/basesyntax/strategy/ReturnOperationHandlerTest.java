package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void apply_shouldAddNewFruit_whenFruitDoesNotExist() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(Storage.inventory, "apple", 5);
        assertEquals(5, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldIncreaseQuantity_whenFruitAlreadyExists() {
        Storage.inventory.put("apple", 10);
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(Storage.inventory, "apple", 3);
        assertEquals(13, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldNotChangeQuantity_whenZeroQuantityProvided() {
        Storage.inventory.put("apple", 10);
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(Storage.inventory, "apple", 0);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldDecreaseQuantity_whenNegativeQuantityProvided() {
        Storage.inventory.put("apple", 10);
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(Storage.inventory, "apple", -4);
        assertEquals(6, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldAccumulateMultipleCalls() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(Storage.inventory, "apple", 2);
        handler.apply(Storage.inventory, "apple", 3);
        handler.apply(Storage.inventory, "apple", -1);
        assertEquals(4, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldWorkIndependentlyForDifferentFruits() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(Storage.inventory, "apple", 3);
        handler.apply(Storage.inventory, "banana", 4);
        assertEquals(3, Storage.inventory.get("apple"));
        assertEquals(4, Storage.inventory.get("banana"));
    }

    @Test
    void apply_shouldAllowNullFruit() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(Storage.inventory, null, 5);
        assertEquals(5, Storage.inventory.get(null));
        handler.apply(Storage.inventory, null, 3);
        assertEquals(8, Storage.inventory.get(null));
    }

    @Test
    void apply_shouldNotAffectPassedMap() {
        Map<String, Integer> localMap = new HashMap<>();
        localMap.put("orange", 10);
        ReturnOperationHandler handler = new ReturnOperationHandler();
        handler.apply(localMap, "orange", 7);
        assertEquals(10, localMap.get("orange"));
        assertEquals(7, Storage.inventory.get("orange"));
    }
}

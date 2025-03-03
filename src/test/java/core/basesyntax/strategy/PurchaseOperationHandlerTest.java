package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private Map<String, Integer> inventory;
    private PurchaseOperationHandler handler;

    @BeforeEach
    void setUp() {
        inventory = new HashMap<>();
        handler = new PurchaseOperationHandler();
    }

    @Test
    void apply_shouldReduceQuantity_whenSufficientInventory() {
        inventory.put("apple", 10);
        handler.apply(inventory, "apple", 3);
        assertEquals(7, inventory.get("apple"));
    }

    @Test
    void apply_shouldThrowException_whenInsufficientInventory() {
        inventory.put("apple", 5);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            handler.apply(inventory, "apple", 6);
        });
        assertEquals("Not enough apple in inventory.", exception.getMessage());
    }

    @Test
    void apply_shouldThrowException_whenFruitNotPresent() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            handler.apply(inventory, "banana", 1);
        });
        assertEquals("Not enough banana in inventory.", exception.getMessage());
    }

    @Test
    void apply_shouldNotChangeQuantity_whenZeroQuantityProvided() {
        inventory.put("apple", 10);
        handler.apply(inventory, "apple", 0);
        assertEquals(10, inventory.get("apple"));
    }

    @Test
    void apply_shouldIncreaseQuantity_whenNegativeQuantityProvided() {
        inventory.put("apple", 10);
        handler.apply(inventory, "apple", -5);
        assertEquals(15, inventory.get("apple"));
    }

    @Test
    void apply_multipleOperations_shouldAccumulateCorrectly() {
        inventory.put("apple", 20);
        handler.apply(inventory, "apple", 5);
        handler.apply(inventory, "apple", 3);
        assertEquals(12, inventory.get("apple"));
    }

    @Test
    void apply_shouldNotAffectOtherFruits() {
        inventory.put("apple", 10);
        inventory.put("banana", 8);
        handler.apply(inventory, "apple", 4);
        assertEquals(6, inventory.get("apple"));
        assertEquals(8, inventory.get("banana"));
    }

    @Test
    void apply_shouldHandleLargePurchase() {
        inventory.put("mango", 100);
        handler.apply(inventory, "mango", 50);
        assertEquals(50, inventory.get("mango"));
    }
}

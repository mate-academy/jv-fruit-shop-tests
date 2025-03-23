package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {

    private ReturnOperationHandler returnOperationHandler;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        inventory = new HashMap<>();
    }

    @Test
    void apply_ShouldAddQuantity_WhenReturnPositiveAmount() {
        inventory.put("apple", 5);

        returnOperationHandler.apply(inventory, "apple", 3);

        assertEquals(8, inventory.get("apple"));
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenReturnZero() {
        inventory.put("apple", 5);

        returnOperationHandler.apply(inventory, "apple", 0);

        assertEquals(5, inventory.get("apple"));
    }

    @Test
    void apply_ShouldThrowException_WhenReturnNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.apply(inventory, "apple", -3));
    }

    @Test
    void apply_ShouldAddQuantity_WhenFruitIsNotInInventory() {
        returnOperationHandler.apply(inventory, "apple", 5);

        assertEquals(5, inventory.get("apple"));
    }

    @Test
    void apply_ShouldHandleMultipleFruits() {
        inventory.put("apple", 5);
        inventory.put("banana", 3);

        returnOperationHandler.apply(inventory, "apple", 2);
        returnOperationHandler.apply(inventory, "banana", 4);

        assertEquals(7, inventory.get("apple"));
        assertEquals(7, inventory.get("banana"));
    }
}

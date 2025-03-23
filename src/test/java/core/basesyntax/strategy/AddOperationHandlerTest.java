package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddOperationHandlerTest {

    private InventoryService inventoryService;
    private AddOperationHandler addOperationHandler;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        addOperationHandler = new AddOperationHandler(inventoryService);
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> addOperationHandler.apply(inventoryService.getInventory(), "apple", -5),
                "Quantity cannot be negative");
    }

    @Test
    void apply_ShouldThrowException_WhenNotEnoughQuantityInInventory() {
        inventoryService.addFruit("apple", 10);
        assertThrows(IllegalArgumentException.class,
                () -> addOperationHandler.apply(inventoryService.getInventory(), "apple", 20),
                "Not enough apple in inventory");
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenQuantityIsZero() {
        inventoryService.addFruit("apple", 10);
        addOperationHandler.apply(inventoryService.getInventory(), "apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldIncreaseQuantity_WhenQuantityIsPositive() {
        inventoryService.addFruit("apple", 10);
        addOperationHandler.apply(inventoryService.getInventory(), "apple", 5);
        assertEquals(5, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldHandleMultipleFruits() {
        inventoryService.addFruit("apple", 10);
        inventoryService.addFruit("banana", 5);
        addOperationHandler.apply(inventoryService.getInventory(), "apple", 3);
        addOperationHandler.apply(inventoryService.getInventory(), "banana", 2);
        assertEquals(7, inventoryService.getQuantity("apple"));
        assertEquals(3, inventoryService.getQuantity("banana"));
    }

    @Test
    void apply_ShouldNotThrowException_WhenAddingZeroQuantityForNewFruit() {
        addOperationHandler.apply(inventoryService.getInventory(), "orange", 0);
        assertEquals(0, inventoryService.getQuantity("orange"));
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsExcessive() {
        inventoryService.addFruit("apple", 5);
        assertThrows(IllegalArgumentException.class,
                () -> addOperationHandler.apply(inventoryService.getInventory(), "apple", 10),
                "Not enough apple in inventory");
    }
}

package core.basesyntax.strategy;

import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                () -> addOperationHandler.apply("apple", -5));
    }

    @Test
    void apply_ShouldAddFruitToInventory() {
        addOperationHandler.apply("banana", 10);
        assertEquals(10, inventoryService.getQuantity("banana"));
    }

    @Test
    void apply_ShouldAddToExistingQuantity() {
        inventoryService.addFruit("apple", 3);
        addOperationHandler.apply("apple", 7);
        assertEquals(20, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenQuantityIsZero() {
        inventoryService.addFruit("apple", 10);
        addOperationHandler.apply("apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }
}

package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {

    private InventoryService inventoryService;
    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    void apply_ShouldIncreaseQuantity_WhenFruitExists() {
        inventoryService.addFruit("apple", 10);
        supplyOperationHandler.apply(inventoryService.getInventory(), "apple", 5);
        assertEquals(15, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldAddFruit_WhenFruitDoesNotExist() {
        supplyOperationHandler.apply(inventoryService.getInventory(), "banana", 5);
        assertEquals(5, inventoryService.getQuantity("banana"));
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                supplyOperationHandler.apply(inventoryService.getInventory(), "apple", -5));
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenQuantityIsZero() {
        inventoryService.addFruit("apple", 10);
        supplyOperationHandler.apply(inventoryService.getInventory(), "apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldIncreaseNewFruitQuantity_WhenNewFruitIsAdded() {
        supplyOperationHandler.apply(inventoryService.getInventory(), "orange", 10);
        assertEquals(10, inventoryService.getQuantity("orange"));
    }

    @Test
    void apply_ShouldHandleMultipleFruitsCorrectly() {
        supplyOperationHandler.apply(inventoryService.getInventory(), "apple", 5);
        supplyOperationHandler.apply(inventoryService.getInventory(), "banana", 10);
        supplyOperationHandler.apply(inventoryService.getInventory(), "apple", 3);
        assertEquals(8, inventoryService.getQuantity("apple"));
        assertEquals(10, inventoryService.getQuantity("banana"));
    }
}

package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {

    private InventoryService inventoryService;
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    void apply_ShouldReturnCurrentBalance_WhenFruitExists() {
        inventoryService.addFruit("apple", 10);
        balanceOperationHandler.apply(inventoryService.getInventory(), "apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldReturnZero_WhenFruitDoesNotExist() {
        balanceOperationHandler.apply(inventoryService.getInventory(), "banana", 0);
        assertEquals(0, inventoryService.getQuantity("banana"));
    }

    @Test
    void apply_ShouldNotChangeInventory_WhenCalled() {
        inventoryService.addFruit("apple", 10);
        balanceOperationHandler.apply(inventoryService.getInventory(), "apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldHandleEmptyInventory() {
        balanceOperationHandler.apply(inventoryService.getInventory(), "apple", 0);
        assertEquals(0, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldReturnZero_WhenFruitIsNotInInventory() {
        balanceOperationHandler.apply(inventoryService.getInventory(), "orange", 0);
        assertEquals(0, inventoryService.getQuantity("orange"));
    }

    @Test
    void apply_ShouldReturnBalanceWithoutChangingInventory_WhenCalled() {
        inventoryService.addFruit("apple", 10);
        balanceOperationHandler.apply(inventoryService.getInventory(), "apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }
}

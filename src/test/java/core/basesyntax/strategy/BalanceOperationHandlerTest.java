package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {

    private InventoryService inventoryService;
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        balanceOperationHandler = new BalanceOperationHandler(inventoryService);
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperationHandler.apply("apple", -5));
    }

    @Test
    void apply_ShouldAddFruitToInventory() {
        balanceOperationHandler.apply("banana", 10);
        assertEquals(10, inventoryService.getQuantity("banana"));
    }

    @Test
    void apply_ShouldAddToExistingQuantity() {
        inventoryService.addFruit("apple", 3);
        balanceOperationHandler.apply("apple", 7);
        assertEquals(20, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenQuantityIsZero() {
        inventoryService.addFruit("apple", 10);
        balanceOperationHandler.apply("apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }
}

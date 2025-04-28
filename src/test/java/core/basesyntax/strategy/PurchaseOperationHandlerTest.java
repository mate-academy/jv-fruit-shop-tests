package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private InventoryService inventoryService;
    private PurchaseOperationHandler handler;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
        inventoryService = new InventoryService();
        handler = new PurchaseOperationHandler();
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.apply("apple", -1),
                "Quantity for purchase cannot be negative");
    }

    @Test
    void apply_ShouldThrowException_WhenNotEnoughStock() {
        inventoryService.addFruit("apple", 5);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply("apple", 10));
        assertEquals("Not enough stock for apple", exception.getMessage());
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenZeroQuantityProvided() {
        inventoryService.addFruit("apple", 10);
        handler.apply("apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }
}

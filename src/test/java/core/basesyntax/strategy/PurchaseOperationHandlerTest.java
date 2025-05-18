package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private InventoryService inventoryService;
    private PurchaseOperationHandler handler;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        handler = new PurchaseOperationHandler();
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.apply("apple", -1)
        );
    }

    @Test
    void apply_ShouldThrowException_WhenNotEnoughStock() {
        Storage.inventory.put("apple", 5);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply("apple", 10)
        );
        assertEquals("Not enough stock for apple", exception.getMessage());
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenZeroQuantityProvided() {
        Storage.inventory.put("apple", 10);
        handler.apply("apple", 0);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @AfterEach
    void setUpAfter() {
        Storage.inventory.clear();
    }
}

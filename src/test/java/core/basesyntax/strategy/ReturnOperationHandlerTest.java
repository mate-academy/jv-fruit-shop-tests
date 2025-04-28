package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {

    private InventoryService inventoryService;
    private ReturnOperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
        inventoryService = new InventoryService();
        returnHandler = new ReturnOperationHandler();
    }

    @Test
    void apply_zeroQuantity_shouldNotModifyInventory() {
        inventoryService.addFruit("apple", 2);
        returnHandler.apply("apple", 0);
        assertEquals(2, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_negativeQuantity_shouldThrowExceptionWithMessage() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> returnHandler.apply("banana", -2)
        );
        assertEquals("Quantity for return cannot be negative", ex.getMessage());
    }
}

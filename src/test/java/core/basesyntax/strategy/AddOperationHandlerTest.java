package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
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
                () -> addOperationHandler.apply(Storage.inventory, "apple", -5),
                "Quantity cannot be negative");
    }

    @Test
    void apply_ShouldThrowException_WhenNotEnoughQuantityInInventory() {
        inventoryService.addFruit("apple", 10);
        assertThrows(IllegalArgumentException.class,
                () -> addOperationHandler.apply(Storage.inventory, "apple", 20),
                "Not enough apple in inventory");
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenQuantityIsZero() {
        inventoryService.addFruit("apple", 10);
        addOperationHandler.apply(Storage.inventory, "apple", 0);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }

    @Test
    void apply_ShouldNotThrowException_WhenAddingZeroQuantityForNewFruit() {
        addOperationHandler.apply(Storage.inventory, "orange", 0);
        assertEquals(0, inventoryService.getQuantity("orange"));
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsExcessive() {
        inventoryService.addFruit("apple", 5);
        assertThrows(IllegalArgumentException.class,
                () -> addOperationHandler.apply(Storage.inventory, "apple", 10),
                "Not enough apple in inventory");
    }
}

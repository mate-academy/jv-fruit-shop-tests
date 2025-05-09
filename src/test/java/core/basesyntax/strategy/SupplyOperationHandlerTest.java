package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {

    private InventoryService inventoryService;
    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
        inventoryService = new InventoryService();
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.apply("apple", -5),
                "Quantity for supply cannot be negative");
    }

    @Test
    void apply_ShouldIncreaseQuantity_WhenFruitExists() {
        inventoryService.addFruit("orange", 10);
        supplyOperationHandler.apply("orange", 5);
        assertEquals(15, inventoryService.getQuantity("orange"));
    }

}

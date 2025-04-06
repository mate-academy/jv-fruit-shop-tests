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
    void apply_ShouldAddFruit_WhenFruitDoesNotExist() {
        supplyOperationHandler.apply(Storage.inventory, "banana", 5);
        assertEquals(5, Storage.inventory.get("banana").intValue());
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                supplyOperationHandler.apply(Storage.inventory, "apple", -5));
    }

    @Test
    void apply_ShouldIncreaseNewFruitQuantity_WhenNewFruitIsAdded() {
        supplyOperationHandler.apply(Storage.inventory, "orange", 10);
        assertEquals(10, Storage.inventory.get("orange").intValue());
    }

    @Test
    void apply_ShouldHandleMultipleFruitsCorrectly() {
        supplyOperationHandler.apply(Storage.inventory, "apple", 5);
        supplyOperationHandler.apply(Storage.inventory, "banana", 10);
        supplyOperationHandler.apply(Storage.inventory, "apple", 3);
        assertEquals(8, Storage.inventory.get("apple").intValue());
        assertEquals(10, Storage.inventory.get("banana").intValue());
    }
}

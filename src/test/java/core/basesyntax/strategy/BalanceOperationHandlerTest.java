package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
        balanceOperationHandler = new BalanceOperationHandler(new InventoryService());
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperationHandler.apply("apple", -5)
        );
    }

    @Test
    void apply_ShouldAddNewFruitToInventory_WhenFruitNotPresent() {
        balanceOperationHandler.apply("banana", 10);
        assertEquals(10, Storage.inventory.get("banana"));
    }

    @Test
    void apply_ShouldOverwriteExistingQuantity_WhenFruitPresent() {
        Storage.inventory.put("apple", 3);
        balanceOperationHandler.apply("apple", 7);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void apply_ShouldNotChangeQuantity_WhenQuantityIsZero() {
        Storage.inventory.put("apple", 10);
        balanceOperationHandler.apply("apple", 0);
        assertEquals(10, Storage.inventory.get("apple"));
    }
}

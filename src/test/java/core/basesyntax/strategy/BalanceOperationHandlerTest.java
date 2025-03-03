package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler();
        Storage.inventory.clear();
    }

    @Test
    void apply_shouldSetNewBalanceForFruit_whenFruitDoesNotExist() {
        handler.apply(Storage.inventory, "apple", 10);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldOverwriteExistingBalance_whenFruitAlreadyExists() {
        Storage.inventory.put("apple", 5);
        handler.apply(Storage.inventory, "apple", 10);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldSetZeroBalance_whenZeroQuantityProvided() {
        handler.apply(Storage.inventory, "apple", 0);
        assertEquals(0, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldHandleNegativeQuantity() {
        handler.apply(Storage.inventory, "apple", -5);
        assertEquals(-5, Storage.inventory.get("apple"));
    }

    @Test
    void apply_shouldNotAffectOtherFruits() {
        Storage.inventory.put("apple", 5);
        Storage.inventory.put("banana", 10);
        handler.apply(Storage.inventory, "apple", 8);
        assertEquals(8, Storage.inventory.get("apple"));
        assertEquals(10, Storage.inventory.get("banana"));
    }

    @Test
    void apply_shouldSetCorrectBalanceAfterMultipleOperations() {
        handler.apply(Storage.inventory, "apple", 10);
        handler.apply(Storage.inventory, "apple", 20);
        assertEquals(20, Storage.inventory.get("apple"));
    }
}

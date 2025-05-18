package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        returnHandler = new ReturnOperationHandler();
    }

    @Test
    void apply_zeroQuantity_shouldNotModifyInventory() {
        Storage.inventory.put("apple", 2);
        returnHandler.apply("apple", 0);
        assertEquals(2, Storage.inventory.get("apple"));
    }

    @Test
    void apply_negativeQuantity_shouldThrowExceptionWithMessage() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> returnHandler.apply("banana", -2)
        );
        assertEquals("Quantity for return cannot be negative", ex.getMessage());
    }

    @Test
    void apply_positiveQuantity_shouldAddToInventory() {
        returnHandler.apply("apple", 5);
        assertEquals(5, Storage.inventory.get("apple"));
    }

    @Test
    void apply_existingFruitPositiveQuantity_shouldIncrementInventory() {
        Storage.inventory.put("banana", 3);
        returnHandler.apply("banana", 2);
        assertEquals(5, Storage.inventory.get("banana"));
    }

    @AfterEach
    void setUpAfter() {
        Storage.inventory.clear();
    }
}

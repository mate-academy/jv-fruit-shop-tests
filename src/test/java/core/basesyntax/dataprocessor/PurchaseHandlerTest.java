package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        FruitDB.getInventory().clear();
        purchaseHandler = new PurchaseHandler();
        FruitDB.add("apple", 50);
        FruitDB.add("banana", 30);
    }

    @Test
    void apply_validPurchase_reducesInventory() {
        purchaseHandler.apply("apple", 20);
        assertEquals(30, FruitDB.getInventory().get("apple").intValue());
    }

    @Test
    void apply_exactInventoryPurchase_reducesInventoryToZero() {
        purchaseHandler.apply("banana", 30);
        assertEquals(0, FruitDB.getInventory().get("banana").intValue());
    }

    @Test
    void apply_insufficientInventory_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseHandler.apply("apple", 60)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void apply_fruitNotInInventory_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseHandler.apply("orange", 10)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseHandler.apply("apple", -10)
        );
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        purchaseHandler.apply("apple", 0);
        assertEquals(50, FruitDB.getInventory().get("apple").intValue());
    }
}

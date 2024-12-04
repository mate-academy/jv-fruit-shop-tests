package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        FruitDB.getInventory().clear();
        purchaseHandler = new PurchaseHandler();
        FruitDB.add(APPLE, 50);
    }

    @Test
    void apply_validPurchase_reducesInventory() {
        purchaseHandler.apply(APPLE, 20);
        assertEquals(30, FruitDB.getInventory().get(APPLE).intValue());
    }

    @Test
    void apply_exactInventoryPurchase_reducesInventoryToZero() {
        purchaseHandler.apply(APPLE, 50);
        assertEquals(0, FruitDB.getInventory().get(APPLE).intValue());
    }

    @Test
    void apply_insufficientInventory_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseHandler.apply(APPLE, 60)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void apply_fruitNotInInventory_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseHandler.apply(BANANA, 10)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseHandler.apply(APPLE, -10)
        );
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        purchaseHandler.apply(APPLE, 0);
        assertEquals(50, FruitDB.getInventory().get(APPLE).intValue());
    }
}

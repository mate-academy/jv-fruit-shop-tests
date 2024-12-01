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
        FruitDB.getInstance().getInventory().clear();
        purchaseHandler = new PurchaseHandler();
        FruitDB.getInstance().add("apple", 50);
        FruitDB.getInstance().add("banana", 30);
    }

    @Test
    void apply_validPurchase_reducesInventory() {
        purchaseHandler.apply("apple", 20);
        assertEquals(30, FruitDB.getInstance().getInventory().get("apple").intValue());
    }

    @Test
    void apply_exactInventoryPurchase_reducesInventoryToZero() {
        purchaseHandler.apply("banana", 30);
        assertEquals(0, FruitDB.getInstance().getInventory().get("banana").intValue());
    }

    @Test
    void apply_insufficientInventory_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> purchaseHandler.apply("apple", 60));
    }

    @Test
    void apply_fruitNotInInventory_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> purchaseHandler.apply("orange", 10));
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> purchaseHandler.apply("apple", -10));
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        purchaseHandler.apply("apple", 0);
        assertEquals(50, FruitDB.getInstance().getInventory().get("apple").intValue());
    }
}

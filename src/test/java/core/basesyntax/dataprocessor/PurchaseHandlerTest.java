package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {

    private FruitDB fruitDB;
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        fruitDB = new FruitDB();
        purchaseHandler = new PurchaseHandler(fruitDB);
        fruitDB.add("apple", 50);
        fruitDB.add("banana", 30);
    }

    @Test
    void apply_validPurchase_reducesInventory() {
        purchaseHandler.apply("apple", 20);
        assertEquals(30, fruitDB.getInventory().get("apple"));
    }

    @Test
    void apply_exactInventoryPurchase_reducesInventoryToZero() {
        purchaseHandler.apply("banana", 30);
        assertEquals(0, fruitDB.getInventory().get("banana"));
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
        assertEquals(50, fruitDB.getInventory().get("apple"));
    }
}

package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void apply_validPurchase_decreasesQuantity() {
        Storage.fruits.put("banana", 100);

        purchaseOperationHandler.apply("banana", 30);

        assertEquals(70, Storage.fruits.get("banana"));
    }

    @Test
    void apply_purchaseZeroQuantity_doesNothing() {
        Storage.fruits.put("apple", 50);

        purchaseOperationHandler.apply("apple", 0);

        assertEquals(50, Storage.fruits.get("apple"));
    }

    @Test
    void apply_purchaseNonExistentFruit_throwsException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> purchaseOperationHandler.apply("mango", 20));

        assertEquals("Fruit does not exist in storage", exception.getMessage());
    }

    @Test
    void apply_purchaseMoreThanAvailable_throwsException() {
        Storage.fruits.put("orange", 10);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> purchaseOperationHandler.apply("orange", 15));

        assertEquals("Not enough stock for orange", exception.getMessage());
    }

    @Test
    void apply_purchaseExactAmount_quantityBecomesZero() {
        Storage.fruits.put("pear", 50);
        purchaseOperationHandler.apply("pear", 50);

        assertEquals(0, Storage.fruits.get("pear"));
    }
}

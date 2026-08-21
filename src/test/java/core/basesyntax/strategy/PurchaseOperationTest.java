package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation purchaseOperation = new PurchaseOperation();

    @Test
    void apply_validValues_ok() {
        int actual = purchaseOperation.apply(100, 50);
        assertEquals(50, actual);
    }

    @Test
    void apply_exactBalance_ok() {
        int actual = purchaseOperation.apply(100, 100);
        assertEquals(0, actual);
    }

    @Test
    void apply_negativeResult_ok() {
        int actual = purchaseOperation.apply(50, 100);
        assertEquals(-50, actual);
    }
}

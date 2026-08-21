package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private final SupplyOperation supplyOperation = new SupplyOperation();

    @Test
    void apply_validValues_ok() {
        int actual = supplyOperation.apply(100, 50);
        assertEquals(150, actual);
    }

    @Test
    void apply_toZero_ok() {
        int actual = supplyOperation.apply(0, 100);
        assertEquals(100, actual);
    }

    @Test
    void apply_zero_ok() {
        int actual = supplyOperation.apply(100, 0);
        assertEquals(100, actual);
    }
}

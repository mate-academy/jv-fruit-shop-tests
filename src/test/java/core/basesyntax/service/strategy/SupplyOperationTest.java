package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    @Test
    void apply_positiveQuantity_addsToCurrent() {
        SupplyOperation handler = new SupplyOperation();
        int result = handler.apply(100, 50);
        assertEquals(150, result);
    }

    @Test
    void apply_zeroQuantity_returnsSameAsCurrent() {
        SupplyOperation handler = new SupplyOperation();
        int result = handler.apply(100, 0);
        assertEquals(100, result);
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        SupplyOperation handler = new SupplyOperation();
        assertThrows(RuntimeException.class, () -> handler.apply(100, -10));
    }
}

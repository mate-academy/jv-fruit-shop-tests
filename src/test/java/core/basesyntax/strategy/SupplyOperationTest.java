package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    @Test
    void shouldIncreaseQuantity() {
        OperationHandler handler = new SupplyOperation();

        Map<String, Integer> result =
                handler.apply(Map.of("banana", 10), "banana", 5);

        assertEquals(15, result.get("banana"));
    }

    @Test
    void shouldThrowForNegativeSupply() {
        OperationHandler handler = new SupplyOperation();

        assertThrows(RuntimeException.class,
                () -> handler.apply(Map.of(), "banana", -5));
    }
}

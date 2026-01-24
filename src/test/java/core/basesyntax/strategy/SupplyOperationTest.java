package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    private static OperationHandler handler;

    @BeforeAll
    static void init() {
        handler = new SupplyOperation();
    }

    @Test
    void apply_ShouldIncreaseQuantity_WhenQuantityIsPositive() {
        Map<String, Integer> result =
                handler.apply(Map.of("banana", 10), "banana", 5);

        assertEquals(15, result.get("banana"));
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(RuntimeException.class,
                () -> handler.apply(Map.of(), "banana", -5));
    }
}

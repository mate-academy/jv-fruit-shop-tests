package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    private static OperationHandler handler;

    @BeforeAll
    static void init() {
        handler = new PurchaseOperation();
    }

    @Test
    void apply_ShouldDecreaseQuantity_WhenEnoughFruit() {
        Map<String, Integer> result =
                handler.apply(Map.of("banana", 10), "banana", 4);

        assertEquals(6, result.get("banana"));
    }

    @Test
    void apply_ShouldThrowException_WhenNotEnoughFruit() {
        assertThrows(RuntimeException.class,
                () -> handler.apply(Map.of("banana", 2), "banana", 5));
    }
}

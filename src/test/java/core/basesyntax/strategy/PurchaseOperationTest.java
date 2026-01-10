package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    @Test
    void shouldDecreaseQuantity() {
        OperationHandler handler = new PurchaseOperation();

        Map<String, Integer> result =
                handler.apply(Map.of("banana", 10), "banana", 4);

        assertEquals(6, result.get("banana"));
    }

    @Test
    void shouldThrowIfNotEnoughFruit() {
        OperationHandler handler = new PurchaseOperation();

        assertThrows(RuntimeException.class,
                () -> handler.apply(Map.of("banana", 2), "banana", 5));
    }
}

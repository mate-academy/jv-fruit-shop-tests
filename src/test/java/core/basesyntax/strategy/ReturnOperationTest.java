package core.basesyntax.strategy;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ReturnOperationTest {

    @Test
    void shouldIncreaseQuantity() {
        OperationHandler handler = new ReturnOperation();

        Map<String, Integer> result =
                handler.apply(Map.of("apple", 5), "apple", 3);

        assertEquals(8, result.get("apple"));
    }

    @Test
    void shouldThrowForNegativeReturn() {
        OperationHandler handler = new ReturnOperation();

        assertThrows(RuntimeException.class,
                () -> handler.apply(Map.of(), "apple", -1));
    }
}

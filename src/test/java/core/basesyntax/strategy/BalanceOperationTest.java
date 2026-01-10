package core.basesyntax.strategy;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BalanceOperationTest {

    @Test
    void shouldSetBalance() {
        OperationHandler handler = new BalanceOperation();

        Map<String, Integer> result =
                handler.apply(Map.of(), "apple", 10);

        assertEquals(10, result.get("apple"));
    }

    @Test
    void shouldThrowForNegativeBalance() {
        OperationHandler handler = new BalanceOperation();

        assertThrows(RuntimeException.class,
                () -> handler.apply(Map.of(), "apple", -1));
    }
}

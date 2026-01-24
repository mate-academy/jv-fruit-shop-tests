package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private static OperationHandler handler;

    @BeforeAll
    static void init() {
        handler = new BalanceOperation();
    }

    @Test
    void apply_ShouldSetBalance_WhenQuantityIsPositive() {
        Map<String, Integer> result =
                handler.apply(Map.of(), "apple", 10);

        assertEquals(10, result.get("apple"));
    }

    @Test
    void apply_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(RuntimeException.class,
                () -> handler.apply(Map.of(), "apple", -1));
    }
}

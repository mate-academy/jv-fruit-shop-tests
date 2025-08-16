package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class IncrementerTest {
    @Test
    void nullQuantity_NotOk() {
        Map<String, Integer> storage = new HashMap<>();
        String fruit = "banana";
        assertThrows(IllegalArgumentException.class, () -> {
            Incrementer.add(storage, fruit, -10);
        });
    }
}

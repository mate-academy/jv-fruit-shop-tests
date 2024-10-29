package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.operations.BalanceOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private Map<String, Integer> storage;
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void handle_shouldUpdateBalance() {
        balanceOperation.handle("apple", 100, storage);
        assertEquals(100, storage.get("apple"));
    }
}

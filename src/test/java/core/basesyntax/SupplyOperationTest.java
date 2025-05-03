package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private Map<String, Integer> storage;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void handle_shouldIncreaseQuantity() {
        storage.put("apple", 50);
        supplyOperation.handle("apple", 30, storage);
        assertEquals(80, storage.get("apple"));
    }
}

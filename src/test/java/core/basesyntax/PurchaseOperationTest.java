package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.operations.PurchaseOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private Map<String, Integer> storage;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void handle_shouldDecreaseQuantity() {
        storage.put("apple", 50);
        purchaseOperation.handle("apple", 30, storage);
        assertEquals(20, storage.get("apple"));
    }
}

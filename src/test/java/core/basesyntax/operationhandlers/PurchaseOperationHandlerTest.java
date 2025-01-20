package core.basesyntax.operationhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final int BANANA_QUANTITY = 80;
    private static final int APPLE_QUANTITY = 10;
    private static final int INVALID_INTEGER = -20;
    private static final String INVALID_TYPE = "mango";

    private OperationHandler operationHandler;
    private Map<String, Integer> storageNew;

    @BeforeEach
    void setUp() {
        storageNew = Storage.getStorage();
        storageNew.put("banana", 100);
        storageNew.put("apple", 100);
        operationHandler = new PurchaseOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.clearAll();
    }

    @Test
    void apply() {
        operationHandler.apply("banana", 20);
        operationHandler.apply("apple", 90);

        assertEquals(BANANA_QUANTITY, Storage.getStorage().get("banana"));
        assertEquals(APPLE_QUANTITY, Storage.getStorage().get("apple"));
    }

    @Test
    void check_negativeQuantity_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            operationHandler.apply("banana", INVALID_INTEGER);
        });
    }

    @Test
    void check_invalidFruitType_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Storage.get(INVALID_TYPE);
        });
    }
}

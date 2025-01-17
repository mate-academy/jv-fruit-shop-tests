package core.basesyntax.operationhandlers;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;
    private static final int INVALID_INTEGER = -20;
    private static final String INVALID_TYPE = "mango";

    private Storage storage;
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    void balance_validData_ok() {
        operationHandler.apply("banana", 152);
        operationHandler.apply("apple", 90);

        Assertions.assertEquals(BANANA_QUANTITY, storage.getStorage().get("banana"));
        Assertions.assertEquals(APPLE_QUANTITY, storage.getStorage().get("apple"));
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
            storage.get(INVALID_TYPE);
        });
    }
}

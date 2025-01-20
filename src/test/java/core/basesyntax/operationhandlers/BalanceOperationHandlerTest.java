package core.basesyntax.operationhandlers;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceOperationHandlerTest {
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int INVALID_INTEGER = -20;
    private static final String INVALID_TYPE = "mango";

    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    void balance_validData_ok() {
        operationHandler.apply("banana", 152);
        operationHandler.apply("apple", 90);

        assertEquals(APPLE_QUANTITY, Storage.getStorage().get(APPLE));
    }

    @Test
    void check_negativeQuantity_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            operationHandler.apply(BANANA, INVALID_INTEGER);
        });
    }

    @Test
    void check_invalidFruitType_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Storage.get(INVALID_TYPE);
        });
    }
}

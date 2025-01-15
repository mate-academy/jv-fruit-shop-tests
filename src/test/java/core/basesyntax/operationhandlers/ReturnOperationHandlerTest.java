package core.basesyntax.operationhandlers;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final int BANANA_QUANTITY = 120;
    private static final int APPLE_QUANTITY = 190;

    private Storage storage;
    private OperationHandler operationHandler;
    private OperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        balanceOperationHandler = new BalanceOperationHandler();
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    void check_returnOperationHandlerIsValid_ok() {
        balanceOperationHandler.apply("banana", 100);
        balanceOperationHandler.apply("apple", 100);

        operationHandler.apply("banana", 20);
        operationHandler.apply("apple", 90);

        Assertions.assertEquals(BANANA_QUANTITY, storage.getStorage().get("banana"));
        Assertions.assertEquals(APPLE_QUANTITY, storage.getStorage().get("apple"));
    }
}

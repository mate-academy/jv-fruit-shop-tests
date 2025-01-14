package core.basesyntax.operationhandlers;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private Storage storage = new Storage();
    private OperationHandler operationHandler;
    private OperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    void check_returnOperationHandlerIsValid_ok() {
        balanceOperationHandler.apply("banana", 100);
        balanceOperationHandler.apply("apple", 100);

        Map<String, Integer> storage1 = new HashMap<>();
        storage1.put("banana", 120);
        storage1.put("apple", 190);

        operationHandler.apply("banana", 20);
        operationHandler.apply("apple", 90);

        Assertions.assertEquals(storage1.get("banana"), storage.getStorage().get("banana"));
        Assertions.assertEquals(storage1.get("apple"), storage.getStorage().get("apple"));
    }
}

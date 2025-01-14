package core.basesyntax.operationhandlers;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private Storage storage = new Storage();
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    void check_balanceOperationHandlerIsValid_ok() {
        Map<String, Integer> storage1 = new HashMap<>();
        storage1.put("banana", 152);
        storage1.put("apple", 90);

        operationHandler.apply("banana", 152);
        operationHandler.apply("apple", 110);

        Assertions.assertEquals(storage1.get("banana"), storage.getStorage().get("banana"));
        Assertions.assertEquals(storage1.get("apple"), storage.getStorage().get("apple"));
    }
}

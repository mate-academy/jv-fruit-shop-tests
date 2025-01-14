package core.basesyntax.operationhandlers;

import java.util.HashMap;
import java.util.Map;
import core.basesyntax.storage.Storage;
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
        storage1.put("banana", 100);
        storage1.put("apple", 110);
        storage1.put("plum", 120);

        operationHandler.apply("banana", 100);
        operationHandler.apply("apple", 110);
        operationHandler.apply("plum", 120);

        Assertions.assertEquals(storage1.get("banana"), storage.getStorage().get("banana"));
        Assertions.assertEquals(storage1.get("apple"), storage.getStorage().get("apple"));
        Assertions.assertEquals(storage1.get("plum"), storage.getStorage().get("plum"));
    }
}

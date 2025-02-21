package handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyHandler();
        Storage.storage.put("banana", 50);
    }

    @Test
    void testOperateSupply_ok() {
        operationHandler.operate(
                "banana", 20);
        Map<String, Integer> expected = Map.of("banana", 70);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }
}

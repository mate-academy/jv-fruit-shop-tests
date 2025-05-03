package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private final OperationHandler operationHandler = new SupplyHandler();

    @BeforeEach
    void set_up() {
        Storage.storage.clear();
        Storage.storage.put("apple", 20);
    }

    @Test
    void process_validSupplyTransaction_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 100);
        operationHandler.handle("apple", 80);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }
}

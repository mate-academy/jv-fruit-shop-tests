package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private final OperationHandler operationHandler = new BalanceHandler();

    @BeforeEach
    void set_up() {
        Storage.storage.clear();
    }

    @Test
    void process_validBalanceTransaction_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 100);
        operationHandler.handle("banana", 100);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }
}

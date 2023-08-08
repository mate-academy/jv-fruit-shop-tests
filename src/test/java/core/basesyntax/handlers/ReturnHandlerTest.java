package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private final OperationHandler operationHandler = new ReturnHandler();

    @BeforeEach
    void set_up() {
        Storage.storage.clear();
        Storage.storage.put("orange", 40);
    }

    @Test
    void process_validReturnTransaction_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("orange", 75);
        operationHandler.handle("orange", 35);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }
}

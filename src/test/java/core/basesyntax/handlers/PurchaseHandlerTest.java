package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private final OperationHandler operationHandler = new PurchaseHandler();

    @BeforeEach
    void set_up() {
        Storage.storage.clear();
        Storage.storage.put("apple", 50);
    }

    @Test
    void process_validPurchaseTransaction_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 15);
        operationHandler.handle("apple", 35);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    void process_invalidData_notOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationHandler.handle("apple", 100)
        );
        assertEquals("The quantity of fruits can't be less than 0", exception.getMessage());
    }
}

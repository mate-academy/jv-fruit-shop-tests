package core.basesyntax;

import core.basesyntax.operations.ReturnOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReturnOperationTest {
    private Map<String, Integer> storage;
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        returnOperation = new ReturnOperation();
    }

    @Test
    void handle_shouldIncreaseQuantityOnReturn() {
        storage.put("apple", 50);
        returnOperation.handle("apple", 30, storage);
        assertEquals(80, storage.get("apple"));
    }
}

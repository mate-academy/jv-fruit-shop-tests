package core.basesyntax.model.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static ReturnOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void return_nullFruitTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> returnOperation.handle(null));
    }

    @Test
    void return_Ok() {
        Storage.getStorage().put("banana", 100);
        returnOperation.handle(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 57));
        Map.Entry<String, Integer> expectedEntry = Map.entry("banana", 157);
        assertTrue(Storage.getStorage().entrySet().contains(expectedEntry));
    }
}

package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private Map<String, Integer> fruitsStorage;

    @BeforeEach
    void setup() {
        fruitsStorage = new HashMap<>();
        fruitsStorage.put("banana", 20);
        Storage.setFruits(fruitsStorage);
    }

    @Test
    void testProcessWithTransaction_successful() {
        ReturnOperation returnOperation = new ReturnOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 5);
        assertDoesNotThrow(() -> returnOperation.processWithTransaction(transaction));
        assertEquals(25, Storage.getFruits().get("banana"));
    }

    @Test
    void testProcessWithTransaction_nullTransaction() {
        ReturnOperation returnOperation = new ReturnOperation();
        assertThrows(RuntimeException.class, () -> returnOperation.processWithTransaction(null));
    }

    @Test
    void testProcessWithTransaction_invalidQuantity() {
        ReturnOperation returnOperation = new ReturnOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", -5);
        assertThrows(RuntimeException.class,
                () -> returnOperation.processWithTransaction(transaction));
    }
}

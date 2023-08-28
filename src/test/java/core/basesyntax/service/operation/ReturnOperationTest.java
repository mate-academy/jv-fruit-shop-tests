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
    private ReturnOperation returnOperation;
    private FruitTransaction transaction;

    @BeforeEach
    void setup() {
        fruitsStorage = new HashMap<>();
        fruitsStorage.put("banana", 20);
        Storage.setFruits(fruitsStorage);
        returnOperation = new ReturnOperation();
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 5);
    }

    @Test
    void testProcessWithTransaction_successful() {
        assertDoesNotThrow(() -> returnOperation.processWithTransaction(transaction));
        assertEquals(25, Storage.getFruits().get("banana"));
    }

    @Test
    void testProcessWithTransaction_nullTransaction_throwsException() {
        assertThrows(RuntimeException.class, () -> returnOperation.processWithTransaction(null));
    }

    @Test
    void testProcessWithTransaction_invalidQuantity_throwsException() {
        transaction.setQuantity(-5);
        assertThrows(RuntimeException.class,
                () -> returnOperation.processWithTransaction(transaction));
    }
}

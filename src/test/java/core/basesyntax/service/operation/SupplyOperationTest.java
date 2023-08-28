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

class SupplyOperationTest {
    private Map<String, Integer> fruitsStorage;
    private SupplyOperation supplyOperation;
    private FruitTransaction transaction;

    @BeforeEach
    void setup() {
        fruitsStorage = new HashMap<>();
        fruitsStorage.put("banana", 20);
        Storage.setFruits(fruitsStorage);
        supplyOperation = new SupplyOperation();
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 10);
    }

    @Test
    void testProcessWithTransaction_successful() {
        assertDoesNotThrow(() -> supplyOperation.processWithTransaction(transaction));
        assertEquals(30, Storage.getFruits().get("banana"));
    }

    @Test
    void testProcessWithTransaction_nullTransaction_throwsException() {
        assertThrows(RuntimeException.class, () -> supplyOperation.processWithTransaction(null));
    }

    @Test
    void testProcessWithTransaction_existingFruit_throwsException() {
        assertDoesNotThrow(() -> supplyOperation.processWithTransaction(transaction));
        assertEquals(30, fruitsStorage.get("banana"));
    }

    @Test
    void testProcessWithTransaction_newFruit_Ok() {
        transaction.setFruit("apple");
        transaction.setQuantity(5);
        assertDoesNotThrow(() -> supplyOperation.processWithTransaction(transaction));
        assertEquals(5, fruitsStorage.get("apple"));
    }
}

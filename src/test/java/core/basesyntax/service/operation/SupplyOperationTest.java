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

    @BeforeEach
    void setup() {
        fruitsStorage = new HashMap<>();
        fruitsStorage.put("banana", 20);
        Storage.setFruits(fruitsStorage);
    }

    @Test
    void testProcessWithTransaction_successful() {
        SupplyOperation supplyOperation = new SupplyOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 10);
        assertDoesNotThrow(() -> supplyOperation.processWithTransaction(transaction));
        assertEquals(30, Storage.getFruits().get("banana"));
    }

    @Test
    void testProcessWithTransaction_nullTransaction() {
        SupplyOperation supplyOperation = new SupplyOperation();
        assertThrows(RuntimeException.class, () -> supplyOperation.processWithTransaction(null));
    }

    @Test
    void testProcessWithTransaction_existingFruit() {
        SupplyOperation supplyOperation = new SupplyOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 10);
        assertDoesNotThrow(() -> supplyOperation.processWithTransaction(transaction));
        assertEquals(30, fruitsStorage.get("banana"));
    }

    @Test
    void testProcessWithTransaction_newFruit() {
        SupplyOperation supplyOperation = new SupplyOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 5);
        assertDoesNotThrow(() -> supplyOperation.processWithTransaction(transaction));
        assertEquals(5, fruitsStorage.get("apple"));
    }
}

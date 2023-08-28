package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private Map<String, Integer> fruitsStorage;
    private PurchaseOperation purchaseOperation;
    private FruitTransaction transaction;

    @BeforeEach
    void setup() {
        fruitsStorage = new HashMap<>();
        fruitsStorage.put("banana", 20);
        Storage.setFruits(fruitsStorage);
        purchaseOperation = new PurchaseOperation();
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10);
    }

    @Test
    void testProcessWithTransaction_successful() {
        purchaseOperation.processWithTransaction(transaction);
        assertEquals(10, Storage.getFruits().get("banana"));
    }

    @Test
    void testProcessWithTransaction_nullTransaction_throwsException() {
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.processWithTransaction(null));
    }

    @Test
    void testProcessWithTransaction_insufficientFruits_throwsException() {
        transaction.setQuantity(30);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.processWithTransaction(transaction));
        assertEquals(20, Storage.getFruits().get("banana"));
    }
}

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

    @BeforeEach
    void setup() {
        fruitsStorage = new HashMap<>();
        fruitsStorage.put("banana", 20);
        Storage.setFruits(fruitsStorage);
    }

    @Test
    void testProcessWithTransaction_successful() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10);
        purchaseOperation.processWithTransaction(transaction);
        assertEquals(10, Storage.getFruits().get("banana"));
    }

    @Test
    void testProcessWithTransaction_nullTransaction() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.processWithTransaction(null));
    }

    @Test
    void testProcessWithTransaction_insufficientFruits() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 30);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.processWithTransaction(transaction));
        assertEquals(20, Storage.getFruits().get("banana"));
    }
}

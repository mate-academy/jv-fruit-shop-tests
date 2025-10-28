package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {

    private final PurchaseOperationHandler handler = new PurchaseOperationHandler();

    @Test
    void apply_validPurchase_shouldDecreaseQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 5);
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);

        handler.apply(transaction, storage);

        assertEquals(5, storage.get("apple"));
    }

    @Test
    void apply_insufficientStock_shouldThrowException() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 15);
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 10);

        assertThrows(RuntimeException.class, () -> handler.apply(transaction, storage));
    }
}

package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private Map<String, Integer> storage;
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        storage.put("apple", 50);
        handler = new PurchaseHandler();
    }

    @Test
    void applyPurchase_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
        handler.apply(transaction, storage);
        assertEquals(40, storage.get("apple"));
    }

    @Test
    void applyMoreQuantity_NotOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 60);
        assertThrows(RuntimeException.class, () -> {
            handler.apply(transaction, storage);
        });
    }
}

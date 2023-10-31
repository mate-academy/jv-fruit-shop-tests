package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    void testHandleTransaction() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 5);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple",
                3);

        purchaseHandler.handleTransaction(transaction, storage);

        assertEquals(7, storage.get("apple"));

        assertEquals(5, storage.get("banana"));
    }

    @Test
    void testHandleTransactionWhenFruitNotInStorage() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 5);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple",
                3);

        purchaseHandler.handleTransaction(transaction, storage);

        assertEquals(-3, storage.get("apple"));

        assertEquals(5, storage.get("banana"));
    }
}

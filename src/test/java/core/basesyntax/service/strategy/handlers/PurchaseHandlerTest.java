package core.basesyntax.service.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
        Storage.fruitsMap.clear();
        Storage.fruitsMap.put("cherry", 100);
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction("p", "cherry", 20);
        purchaseHandler.handleTransaction(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("cherry", 80);
        assertEquals(expected.entrySet(), Storage.fruitsMap.entrySet());
    }

    @Test
    void handleTransaction_notExistingFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction("p", "banana", 101);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleTransaction(transaction));
    }

    @Test
    void handleTransaction_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction("p", "cherry", 1000);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleTransaction(transaction));
    }
}

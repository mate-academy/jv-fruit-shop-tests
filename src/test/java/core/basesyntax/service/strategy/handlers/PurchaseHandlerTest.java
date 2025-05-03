package core.basesyntax.service.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsMap.clear();
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        Storage.fruitsMap.put("cherry", 100);
        FruitTransaction transaction = new FruitTransaction("p", "cherry", 20);
        purchaseHandler.handleTransaction(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("cherry", 80);
        assertEquals(expected.entrySet(), Storage.fruitsMap.entrySet());
    }
}

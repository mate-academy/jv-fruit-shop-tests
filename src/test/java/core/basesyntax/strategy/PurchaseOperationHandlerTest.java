package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    @BeforeAll
    static void clear() {
        Storage.storage.clear();
    }

    @AfterEach
    void clearAfter() {
        Storage.storage.clear();
    }

    @Test
    void when_PurchaseValid_Ok() {
        Storage.storage.put("banana", 100);
        FruitTransaction purchase = new FruitTransaction(
                Operation.PURCHASE,
                "banana",
                100
        );
        OperationHandler purchaseOperation = new PurchaseOperationHandler();
        purchaseOperation.processOperation(purchase);
        Map<String, Integer> expected = Map.of("banana", 0);
        assertEquals(expected, Storage.storage);
    }

    @Test
    void when_PurchaseInvalid_NotOk() {
        Storage.storage.put("banana", 100);
        FruitTransaction purchase = new FruitTransaction(
                Operation.PURCHASE,
                "banana",
                1000
        );
        OperationHandler purchaseOperation = new PurchaseOperationHandler();
        assertThrows(RuntimeException.class, () -> purchaseOperation.processOperation(purchase));
    }
}

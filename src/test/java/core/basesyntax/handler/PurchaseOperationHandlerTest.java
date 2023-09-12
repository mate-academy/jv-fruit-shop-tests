package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.STORAGE.clear();
    }

    @Test
    void processTransaction_quantityMoreAmount_notOk() {
        FruitStorage.STORAGE.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 100);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.process(transaction)
        );
    }

    @Test
    void processTransaction_quantityLessAmount_notOk() {
        FruitStorage.STORAGE.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 30);
        assertDoesNotThrow(() -> purchaseOperationHandler.process(transaction));

        int availableAmount = FruitStorage.STORAGE.get("banana");
        assertEquals(20, availableAmount);
    }
}

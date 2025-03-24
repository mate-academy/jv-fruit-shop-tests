package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void testPurchaseOperation() {
        Storage.storage.put("banana", 100);
        OperationHandler handler = new PurchaseOperation();

        handler.handle(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                30
        ));
        assertEquals(70, Storage.storage.get("banana").intValue());
    }

    @Test
    void testPurchaseOperationInsufficientStock() {
        Storage.storage.put("banana", 20);
        OperationHandler handler = new PurchaseOperation();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.handle(new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "banana",
                        30
                )));

        assertEquals("Not enough banana in storage", exception.getMessage());
    }
}

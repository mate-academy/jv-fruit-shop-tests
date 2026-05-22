package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void purchaseOperation_shouldDecreaseFruitQuantity() {
        storage.getFruits().put("apple", 10);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 3
        );
        PurchaseOperationHandler handler = new PurchaseOperationHandler(storage);
        handler.apply(transaction);
        assertEquals(7, storage.getFruits().get("apple"));
    }

    @Test
    void purchaseOperation_noFruitTypeInStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5
        );
        PurchaseOperationHandler handler = new PurchaseOperationHandler(storage);
        assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));
    }

    @Test
    void purchaseOperation_notEnoughFruits_shouldThrowException() {
        storage.getFruits().put("apple", 2);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 5
        );
        PurchaseOperationHandler handler = new PurchaseOperationHandler(storage);
        assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));
    }
}

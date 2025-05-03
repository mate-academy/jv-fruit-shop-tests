package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler purchaseOperationHandler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        storage = new Storage();
    }

    @Test
    public void handle_validTransaction_Ok() {
        storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);
        purchaseOperationHandler.handle(transaction);
        int expected = 5;
        int actual = storage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void handle_quantityGreaterThanAvailable_exceptionThrown_notOk() {
        storage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.handle(transaction));
    }

    @Test
    public void handle_negativeQuantity_exceptionThrown_notOk() {
        storage.put("orange", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", -5);
        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.handle(transaction));
    }
}

package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PurchaseOperationHandler();
        Storage.clear();
        Storage.setFruitQuantity("banana", 100);
    }

    @Test
    void apply_sufficientStock_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 30);
        handler.apply(transaction);
        assertEquals(70, Storage.getFruitQuantity("banana"));
    }

    @Test
    void apply_insufficientStock_notOk() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 150);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));
        assertEquals("Not enough stock for banana", exception.getMessage());
    }

    @Test
    void apply_noStock_notOk() {
        Storage.clear();
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 10);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));
        assertEquals("Not enough stock for banana", exception.getMessage());
    }
}

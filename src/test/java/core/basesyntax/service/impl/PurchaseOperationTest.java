package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        Storage.putFruit("banana", 50);
    }

    @Test
    void handle_validPurchase_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        purchaseOperation.handle(transaction);
        assertEquals(30, Storage.getFruitQuantity("banana"));
    }

    @Test
    void handle_notEnoughFruits_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 100);
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(transaction));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}

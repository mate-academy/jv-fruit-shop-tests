package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitStorage storage;
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        purchaseOperation = new PurchaseOperation(storage);
    }

    @Test
    void apply_validPurchase_ok() {
        storage.setQuantity("banana", 100);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                30
        );
        purchaseOperation.apply(transaction);
        int actual = storage.getQuantity("banana");
        assertEquals(70, actual);
    }

    @Test
    void apply_notEnoughFruit_notOk() {
        storage.setQuantity("banana", 20);

        FruitTransaction transaction = new FruitTransaction(
                 FruitTransaction.Operation.PURCHASE,
                 "banana",
                 30
         );
        assertThrows(
                 RuntimeException.class,
                 () -> purchaseOperation.apply(transaction)
        );

    }

}

package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class PurchaseTransactionTest {

    @Test
    void execute_removesFruitQuantity() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 100);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.PURCHASE,
                "banana",
                30
        );

        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();

        purchaseTransaction.execute(transaction, storage);

        assertEquals(70, storage.getQuantity("banana"));
    }

    @Test
    void execute_throwsExceptionWhenNotEnoughFruit() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 10);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.PURCHASE,
                "banana",
                20
        );

        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseTransaction.execute(transaction, storage)
        );

        assertEquals(
                "Not enough fruit in storage",
                exception.getMessage()
        );
    }
}

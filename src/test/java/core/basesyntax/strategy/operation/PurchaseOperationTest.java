package core.basesyntax.strategy.operation;

import core.basesyntax.data.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation purchaseOperation = new PurchaseOperation();

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_validPurchaseTransaction_reducesStock() {
        Storage.add("banana", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20);

        System.out.println("Before purchase: " + Storage.getInventory().get("banana"));
        purchaseOperation.apply(transaction);
        System.out.println("After purchase: " + Storage.getInventory().get("banana"));
        
        Assertions.assertEquals(30, Storage.getInventory().get("banana"));
    }

    @Test
    void apply_purchaseMoreThanStock_throwsException() {
        Storage.add("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10);

        Assertions.assertThrows(IllegalStateException.class,
                () -> purchaseOperation.apply(transaction));
    }
}

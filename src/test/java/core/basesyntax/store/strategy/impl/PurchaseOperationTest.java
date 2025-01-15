package core.basesyntax.store.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.store.db.Storage;
import core.basesyntax.store.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
        Storage.modifyFruitStorage("apple", 100);
        Storage.modifyFruitStorage("banana", 50);

        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void apply_shouldReduceFruitQuantityWhenPurchasing() {
        String fruit = "apple";
        int quantityToPurchase = 30;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, quantityToPurchase);

        purchaseOperation.apply(transaction);

        int result = Storage.getFruitQuantity(fruit);
        assertEquals(70, result);
    }

    @Test
    void apply_shouldNotAllowNegativeQuantity() {
        String fruit = "banana";
        int quantityToPurchase = 100;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, quantityToPurchase);

        assertThrows(IllegalStateException.class, () -> {
            purchaseOperation.apply(transaction);
        });
    }

    @Test
    void apply_shouldHandleZeroPurchaseQuantity() {
        String fruit = "apple";
        int quantityToPurchase = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, quantityToPurchase);

        purchaseOperation.apply(transaction);

        int result = Storage.getFruitQuantity(fruit);
        assertEquals(100, result);
    }
}

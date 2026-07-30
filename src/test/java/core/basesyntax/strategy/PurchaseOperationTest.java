package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation purchaseOperation = new PurchaseOperation();

    @BeforeEach
    void tearUp() {
        Storage.getFruits().clear();
    }

    @Test
    void apply_validPurchaseTransaction_removeFruitsFromStorage() {
        Storage.getFruits().put("apple", 666);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 666);
        purchaseOperation.apply(transaction);
        int currentStorageQuantity = Storage.getFruits().get("apple");
        assertEquals(0,currentStorageQuantity,
                "Storage should contain 666 - 666 = 0 apples after PURCHASE operation");
    }

    @Test
    void apply_negativeNumberQuantityPurchaseTransaction_throwRuntimeExeption() {
        Storage.getFruits().put("banana", 30);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", -20);
        Assertions.assertThrows(RuntimeException.class, () -> {
            purchaseOperation.apply(transaction);
        }, "Purchase operation should throw exception if quantity is less than 0");
    }

    @Test
    void apply_negativeQuantityPurchaseTransaction_throwRuntimeExeption() {
        Storage.getFruits().put("banana", 30);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 31);
        Assertions.assertThrows(RuntimeException.class, () -> {
            purchaseOperation.apply(transaction);
        }, "Purchase operation should throw exception if quantity sum is less than 0");
    }
}

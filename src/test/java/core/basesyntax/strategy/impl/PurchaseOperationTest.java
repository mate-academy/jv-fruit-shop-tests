package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

class PurchaseOperationTest {
    private HashMap<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
    }

    @Test
    void purchasePositive_OK() {
        String fruit = "banana";
        int quantity = 100;
        int purchaseQuantity = 15;
        Operation operation = Operation.PURCHASE;
        storage.put(fruit, quantity);
        FruitTransaction transaction = new FruitTransaction(operation, fruit, purchaseQuantity);
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        purchaseOperation.handle(storage, transaction);
        assertEquals(quantity - purchaseQuantity, storage.get(fruit));
    }

    @Test
    void purchaseZero_OK() {
        String fruit = "banana";
        int quantity = 100;
        int purchaseQuantity = 0;
        Operation operation = Operation.PURCHASE;
        storage.put(fruit, quantity);
        FruitTransaction transaction = new FruitTransaction(operation, fruit, purchaseQuantity);
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        purchaseOperation.handle(storage, transaction);
        assertEquals(quantity, storage.get(fruit));
    }

    @Test
    void purchaseNegative_NotOK() {
        String fruit = "banana";
        int quantity = 100;
        int purchaseQuantity = -25;
        Operation operation = Operation.PURCHASE;
        storage.put(fruit, quantity);
        FruitTransaction transaction = new FruitTransaction(operation, fruit, purchaseQuantity);
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(storage, transaction));
    }
}

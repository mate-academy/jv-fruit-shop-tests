package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class PurchaseOperationTest {
    private FruitStorage storage;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        purchaseOperation = new PurchaseOperation(storage);
    }

    @Test
    public void handle_ShouldDecreaseQuantity_WhenSufficientQuantity() {
        storage.updateFruitQuantity("apple", 20);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        purchaseOperation.handle(transaction);
        assertEquals(10, storage.getFruitQuantity("apple"));
    }

    @Test
    public void handle_ShouldThrowException_WhenInsufficientQuantity() {
        storage.updateFruitQuantity("banana", 5);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                purchaseOperation.handle(transaction);
            }
        });
    }

    @Test
    public void handle_ShouldSetQuantityToZero_WhenExactQuantityPurchased() {
        storage.updateFruitQuantity("orange", 15);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setQuantity(15);
        purchaseOperation.handle(transaction);
        assertEquals(0, storage.getFruitQuantity("orange"));
    }

    @Test
    public void handle_ShouldNotChangeStorage_WhenPurchaseQuantityIsZero() {
        storage.updateFruitQuantity("grape", 10);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("grape");
        transaction.setQuantity(0);
        purchaseOperation.handle(transaction);
        assertEquals(10, storage.getFruitQuantity("grape"));
    }

}

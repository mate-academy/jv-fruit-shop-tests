package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.exception.InvalidOperatioExeption;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static Storage storageImpl;
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void setUp() {
        storageImpl = new StorageImpl();
        purchaseOperation = new PurchaseOperation(storageImpl);
    }

    @Test
    void process_enoughQuantity_successfullyUpdatedStorage() {
        Storage.storage.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, "banana", 50);
        purchaseOperation.process(fruitTransaction);
        int updatedQuantity = Storage.storage.get("banana");
        assertEquals(50, updatedQuantity);
    }

    @Test
    void process_insufficientQuantity_throwsInvalidOperationException() {
        Storage.storage.put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, "apple", 150);
        assertThrows(InvalidOperatioExeption.class, () ->
                purchaseOperation.process(fruitTransaction));
    }

    @Test
    void process_zeroQuantity_ok() {
        Storage.storage.put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, "apple", 0);
        purchaseOperation.process(fruitTransaction);
        int updatedQuantity = Storage.storage.get("apple");
        assertEquals(100, updatedQuantity);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}

package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private StorageService storageService;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        purchaseOperation = new PurchaseOperation(storageService);
        Storage.clear();
    }

    @Test
    void handle_validTransaction_shouldRemoveFruitFromStorage() {
        Storage.addFruit("apple",100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30
        );

        purchaseOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(70, (int) fruits.get("apple"));
    }

    @Test
    void handle_removeMoreThanExists_shouldThrowException() {
        Storage.addFruit("banana",20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 30
        );

        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.handle(transaction));
    }

    @Test
    void handle_nonExistentFruit_shouldThrowException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 10
        );

        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.handle(transaction));
    }

    @Test
    void handle_multiplePurchases_shouldUpdateStorageCorrectly() {
        Storage.addFruit("apple",100);
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30
        );
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20
        );

        purchaseOperation.handle(transaction1);
        purchaseOperation.handle(transaction2);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(50, (int) fruits.get("apple"));
    }
}

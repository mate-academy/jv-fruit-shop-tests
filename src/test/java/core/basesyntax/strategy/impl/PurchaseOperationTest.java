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
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final int INITIAL_APPLE_QUANTITY = 100;
    private static final int INITIAL_BANANA_QUANTITY = 20;
    private static final int PURCHASE_APPLE_QUANTITY_1 = 30;
    private static final int PURCHASE_APPLE_QUANTITY_2 = 20;
    private static final int PURCHASE_BANANA_QUANTITY = 30;
    private static final int PURCHASE_ORANGE_QUANTITY = 10;
    private static final int EXPECTED_APPLE_QUANTITY_AFTER_SINGLE_PURCHASE = 70;
    private static final int EXPECTED_APPLE_QUANTITY_AFTER_MULTIPLE_PURCHASES = 50;
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
        Storage.addFruit(APPLE, INITIAL_APPLE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, PURCHASE_APPLE_QUANTITY_1
        );

        purchaseOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_APPLE_QUANTITY_AFTER_SINGLE_PURCHASE, (int) fruits.get(APPLE));
    }

    @Test
    void handle_removeMoreThanExists_shouldThrowException() {
        Storage.addFruit(BANANA, INITIAL_BANANA_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, BANANA, PURCHASE_BANANA_QUANTITY
        );

        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.handle(transaction));
    }

    @Test
    void handle_nonExistentFruit_shouldThrowException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, ORANGE, PURCHASE_ORANGE_QUANTITY
        );

        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.handle(transaction));
    }

    @Test
    void handle_multiplePurchases_shouldUpdateStorageCorrectly() {
        Storage.addFruit(APPLE, INITIAL_APPLE_QUANTITY);
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, PURCHASE_APPLE_QUANTITY_1
        );
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, PURCHASE_APPLE_QUANTITY_2
        );

        purchaseOperation.handle(transaction1);
        purchaseOperation.handle(transaction2);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_APPLE_QUANTITY_AFTER_MULTIPLE_PURCHASES, (int) fruits.get(APPLE));
    }
}


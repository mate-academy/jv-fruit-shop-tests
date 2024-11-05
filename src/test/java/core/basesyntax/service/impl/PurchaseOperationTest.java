package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String GRAPE = "grape";

    private static final int INITIAL_QUANTITY_APPLE = 20;
    private static final int PURCHASE_QUANTITY_APPLE = 10;
    private static final int EXPECTED_QUANTITY_APPLE = 10;

    private static final int INITIAL_QUANTITY_BANANA = 5;
    private static final int PURCHASE_QUANTITY_BANANA = 10;

    private static final int INITIAL_QUANTITY_ORANGE = 15;
    private static final int PURCHASE_QUANTITY_ORANGE = 15;
    private static final int EXPECTED_QUANTITY_ORANGE = 0;

    private static final int INITIAL_QUANTITY_GRAPE = 10;
    private static final int PURCHASE_QUANTITY_ZERO = 0;
    private static final int EXPECTED_QUANTITY_GRAPE = 10;

    private FruitStorage storage;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        purchaseOperation = new PurchaseOperation(storage);
    }

    @Test
    public void handle_ShouldDecreaseQuantity_WhenSufficientQuantity() {
        storage.updateFruitQuantity(APPLE, INITIAL_QUANTITY_APPLE);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(APPLE);
        transaction.setQuantity(PURCHASE_QUANTITY_APPLE);
        purchaseOperation.handle(transaction);
        assertEquals(EXPECTED_QUANTITY_APPLE, storage.getFruitQuantity(APPLE));
    }

    @Test
    public void handle_ShouldThrowException_WhenInsufficientQuantity() {
        storage.updateFruitQuantity(BANANA, INITIAL_QUANTITY_BANANA);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(BANANA);
        transaction.setQuantity(PURCHASE_QUANTITY_BANANA);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseOperation.handle(transaction),
                "Expected IllegalArgumentException when quantity is insufficient."
        );
        String expectedMessage = "Insufficient inventory to purchase " + BANANA;
        assertEquals(expectedMessage, exception.getMessage(),
                "Exception message should indicate insufficient inventory for " + BANANA);
    }

    @Test
    public void handle_ShouldSetQuantityToZero_WhenExactQuantityPurchased() {
        storage.updateFruitQuantity(ORANGE, INITIAL_QUANTITY_ORANGE);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(ORANGE);
        transaction.setQuantity(PURCHASE_QUANTITY_ORANGE);
        purchaseOperation.handle(transaction);
        assertEquals(EXPECTED_QUANTITY_ORANGE, storage.getFruitQuantity(ORANGE));
    }

    @Test
    public void handle_ShouldNotChangeStorage_WhenPurchaseQuantityIsZero() {
        storage.updateFruitQuantity(GRAPE, INITIAL_QUANTITY_GRAPE);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(GRAPE);
        transaction.setQuantity(PURCHASE_QUANTITY_ZERO);
        purchaseOperation.handle(transaction);
        assertEquals(EXPECTED_QUANTITY_GRAPE, storage.getFruitQuantity(GRAPE));
    }

}

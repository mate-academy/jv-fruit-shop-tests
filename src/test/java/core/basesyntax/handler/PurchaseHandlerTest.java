package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {

    private PurchaseHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.modifyFruitStorage("apple", 100);
        Storage.modifyFruitStorage("banana", 50);
        purchaseOperation = new PurchaseHandler();
    }

    @Test
    void apply_shouldReduceFruitQuantityWhenPurchasing() {
        String fruit = "apple";
        int quantityToPurchase = 30;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, quantityToPurchase);

        purchaseOperation.handle(transaction);

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
            purchaseOperation.handle(transaction);
        });
    }

    @AfterEach
    void clearStorage() {
        Storage.clearStorage();
    }
}

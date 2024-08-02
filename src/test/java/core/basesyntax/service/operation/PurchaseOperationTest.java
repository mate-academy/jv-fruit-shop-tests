package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    @AfterEach
    void afterEach_setUp() {
        Storage.getFruitTransactions().clear();
    }

    @Test
    @DisplayName("Purchase operation test")
    void calculateQuantityPurchaseOperation_ok() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        purchaseOperation.calculateQuantity(FruitTransaction.FruitName.APPLE, 20);
        int expectedStorageSizeAfterPurchaseOperation = 1;
        int actualStorageSizeAfterPurchaseOperation = Storage.getFruitTransactions().size();
        assertEquals(expectedStorageSizeAfterPurchaseOperation,
                     actualStorageSizeAfterPurchaseOperation);
    }

    @Test
    @DisplayName("Purchase operation validate fruitName")
    void calculateQuantityFruitNameNull_notOk() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        assertThrows(RuntimeException.class, () -> purchaseOperation.validateFruitName(null));
    }

    @Test
    @DisplayName("Purchase operation validate fruitName")
    void calculateQuantityNegative_notOk() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        assertThrows(RuntimeException.class, () -> purchaseOperation.validateQuantity(-10));
    }
}

package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    @Test
    @DisplayName("Purchase operation test")
    void purchaseOperationGetQuantity_ok() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        int expectedPurchaseOperationResult = 10;
        int actualPurchaseOperationResult = purchaseOperation.getQuantity(20, 10);
        assertEquals(expectedPurchaseOperationResult, actualPurchaseOperationResult);
    }

    @Test
    @DisplayName("Purchase operation invalid test")
    void purchaseOperationGetWrongQuantity_notOk() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        int expectedPurchaseOperationResult = 11;
        int actualPurchaseOperationResult = purchaseOperation.getQuantity(20, 10);
        assertNotEquals(expectedPurchaseOperationResult, actualPurchaseOperationResult);
    }
}

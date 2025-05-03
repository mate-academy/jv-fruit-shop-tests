package core.basesyntax.operationhandler.operation;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation purchaseOperation = new PurchaseOperation();

    @BeforeEach
    void setup() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void testPurchaseBanana_OK() {
        Storage.getFruitStorage().put("banana", 16);
        purchaseOperation.handleOperation("banana",14);
        assertEquals(2, Storage.getFruitStorage().get("banana"));
    }

    @Test
    void testRemoveTooMuchBanana_NotOK() {
        boolean thrown = false;

        try {
            Storage.getFruitStorage().put("banana", 16);
            purchaseOperation.handleOperation("banana",17);
        } catch (RuntimeException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}

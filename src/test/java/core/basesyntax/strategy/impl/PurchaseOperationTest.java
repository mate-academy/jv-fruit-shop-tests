package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void initializeService() {
        purchaseOperation = new PurchaseOperation();
        Storage.cleanStorage();
    }

    @Test
    void doTransaction_acceptableAmount_ok() {
        Storage.setFruitQuantity("banana", 400);
        purchaseOperation.doTransaction("banana", 400);
        int expected = 0;
        int actual = Storage.getFruitQuantity("banana");
        assertEquals(expected, actual);
    }

    @Test
    void doTransaction_notEnoughAmount_ok() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.doTransaction("banana", 400));
    }
}

package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation purchaseOperation = new PurchaseOperation();

    @AfterEach
    void tearDown() {
        Storage.getInstance().getFruitStorage().clear();
    }

    @Test
    void handle_enoughFruit_decreasesFruitQuantity() {
        Storage.getInstance().getFruitStorage().put("banana", 20);

        purchaseOperation.handle("banana", 7);

        assertEquals(13, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void handle_notEnoughFruit_throwsException() {
        Storage.getInstance().getFruitStorage().put("banana", 5);

        assertThrows(RuntimeException.class, () -> purchaseOperation.handle("banana", 7));
    }

    @Test
    void handle_missingFruit_throwsException() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle("banana", 7));
    }
}

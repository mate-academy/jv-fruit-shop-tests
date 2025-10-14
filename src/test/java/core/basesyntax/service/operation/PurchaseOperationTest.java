package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private Storage storage;

    @BeforeEach
    void init() {
        storage = new Storage();
    }

    @Test
    void handle_shouldDecreaseQuantityOnPurchase() {
        storage.put("apple", 10);
        PurchaseOperation op = new PurchaseOperation(storage);
        op.handle(new FruitTransaction(Operation.PURCHASE, "apple", 3));
        assertEquals(7, storage.get("apple"));
    }
}

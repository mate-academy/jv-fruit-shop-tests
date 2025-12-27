package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    void handleTransaction_enoughFruit_ok() {
        Storage.getStorageFruit().put("banana", 95);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 5);
        purchaseOperationHandler.handleTransaction(fruitTransaction);
        assertEquals(90,Storage.getStorageFruit().get("banana"));
    }

    @Test
    void handlerTransaction_buyAll_ok() {
        Storage.getStorageFruit().put("banana", 15);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 15);
        purchaseOperationHandler.handleTransaction(fruitTransaction);
        assertEquals(0,Storage.getStorageFruit().get("banana"));
    }

    @Test
    void handlerTransaction_notEnoughFruit_notOk() {
        Storage.getStorageFruit().put("banana", 2);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 15);
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleTransaction(fruitTransaction));
        assertTrue(runtimeException.getMessage()
                .contains("There isn't that much fruit in the store"));
    }

    @AfterEach
    void tearDown() {
        Storage.getStorageFruit().clear();
    }
}


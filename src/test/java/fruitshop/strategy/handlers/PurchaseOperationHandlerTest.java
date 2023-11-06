package fruitshop.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();

    @Test
    void processPurchaseOperation_quantityLessThanAmount_notOk() {
        Storage.getStorage().put("apple", 15);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.calculateOperation(fruitTransaction));
    }

    @Test
    void processPurchaseOperation_ok() {
        Storage.getStorage().put("apple", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30);
        assertDoesNotThrow(() -> purchaseOperationHandler.calculateOperation(fruitTransaction));
        int actual = Storage.getStorage().get("apple");
        assertEquals(20, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}

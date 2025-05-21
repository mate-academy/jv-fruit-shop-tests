package service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private final OperationHandler operationHandler = new PurchaseOperation();

    @AfterEach
    void cleanUp() {
        Storage.clearStorage();
    }

    @Test
    void testPurchaseOperation_ok() {
        Storage.add("orange", 50);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(20);
        assertDoesNotThrow(() -> operationHandler.performOperation(fruitTransaction));
        assertEquals(30, Storage.getAmount("orange"));
    }

    @Test
    void testNegativePurchaseOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () -> fruitTransaction.setQuantity(-10));
    }
}

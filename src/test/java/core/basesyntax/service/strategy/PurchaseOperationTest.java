package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperation();
    }

    @Test
    void operate_nullTransaction_notOk() {
        String expectedMessage = "Transaction can not be null";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.operate(null));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("apple");
        purchaseTransaction.setQuantity(-20);
        String expectedMessage = "Quantity can't be negative: "
                + purchaseTransaction.getQuantity();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.operate(purchaseTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_nonExistentFruit_notOk() {
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 50);
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("mango");
        purchaseTransaction.setQuantity(20);
        String expectedMessage = "There is no such fruit in storage: "
                + purchaseTransaction.getFruit();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.operate(purchaseTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_overBalancePurchase_notOk() {
        Storage.fruits.put("banana", 10);
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("banana");
        purchaseTransaction.setQuantity(50);
        String expectedMessage = "The purchase quantity is more than balance: "
                + purchaseTransaction.getQuantity();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.operate(purchaseTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_underBalancePurchase_ok() {
        Storage.fruits.put("apple", 100);
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("apple");
        purchaseTransaction.setQuantity(20);
        int expectedQuantityAfterPurchase = 80;
        purchaseOperationHandler.operate(purchaseTransaction);
        assertEquals(expectedQuantityAfterPurchase, Storage.fruits.get("apple"));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}

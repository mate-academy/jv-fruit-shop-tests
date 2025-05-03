package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private final OperationHandler handler = new PurchaseOperationHandler();

    @Test
    void operate_purchaseOperation_ok() {
        Storage.fruits.put("apple", 20);
        FruitTransaction transactionPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5);
        Integer expected = Storage.fruits.get("apple") - transactionPurchase.getQuantity();
        handler.operate(transactionPurchase);
        Integer actual = Storage.fruits.get("apple");
        assertEquals(expected, actual, "Test failed! You should returned next quantity "
                + expected + " but you returned "
                + actual);
    }

    @Test
    void operate_negativePurchaseOperation_notOk() {
        Storage.fruits.put("banana", 2);
        FruitTransaction transactionPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
        assertThrows(RuntimeException.class, () -> handler.operate(transactionPurchase),
                "RuntimeException should be thrown if quantity is negative");
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}

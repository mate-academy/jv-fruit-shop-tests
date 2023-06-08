package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    @Test
    void operate_balanceOperation_ok() {
        FruitTransaction transactionBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "pear", 15);
        Integer expected = transactionBalance.getQuantity();
        OperationHandler handler = new BalanceOperationHandler();
        handler.operate(transactionBalance);
        Integer actual = Storage.fruits.get("pear");
        assertEquals(expected, actual, "Test failed! You should returned next quantity "
                + expected + " but you returned "
                + actual);
    }

    @Test
    void operate_purchaseOperation_ok() {
        Storage.fruits.put("apple", 20);
        FruitTransaction transactionPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5);
        Integer expected = Storage.fruits.get("apple") - transactionPurchase.getQuantity();
        OperationHandler handler = new PurchaseOperationHandler();
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
        OperationHandler handler = new PurchaseOperationHandler();
        assertThrows(RuntimeException.class, () -> handler.operate(transactionPurchase),
                "RuntimeException should be thrown if quantity is negative");
    }

    @Test
    void operate_returnOperation_ok() {
        Storage.fruits.put("orange", 30);
        FruitTransaction transactionReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 15);
        Integer expected = Storage.fruits.get("orange") + transactionReturn.getQuantity();
        OperationHandler handler = new ReturnOperationHandler();
        handler.operate(transactionReturn);
        Integer actual = Storage.fruits.get("orange");
        assertEquals(expected, actual, "Test failed! You should returned next quantity "
                + expected + " but you returned "
                + actual);
    }

    @Test
    void operate_supplyOperation_ok() {
        Storage.fruits.put("pineapple", 8);
        FruitTransaction transactionSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pineapple", 7);
        Integer expected = Storage.fruits.get("pineapple") + transactionSupply.getQuantity();
        OperationHandler handler = new SupplyOperationHandler();
        handler.operate(transactionSupply);
        Integer actual = Storage.fruits.get("pineapple");
        assertEquals(expected, actual, "Test failed! You should returned next quantity "
                + expected + " but you returned "
                + actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}

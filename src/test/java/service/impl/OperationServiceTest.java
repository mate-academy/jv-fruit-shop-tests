package service.impl;

import db.Storage;
import java.util.List;
import model.FruitTransaction;
import model.Operation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.OperationHandler;

class OperationServiceTest {
    private static OperationHandler operationHandler;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void initTransactionsList() {
        fruitTransactions = List.of(
                new FruitTransaction(Operation.PURCHASE, "banana", 7),
                new FruitTransaction(Operation.BALANCE, "banana", 8),
                new FruitTransaction(Operation.RETURN, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 3),
                new FruitTransaction(Operation.SUPPLY, "apple", 6)
        );
    }

    @BeforeEach
    void addFruitBalanceToStorage() {
        Storage.STORAGE.put("banana", 10);
    }

    @AfterAll
    static void clearStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    void purchaseOperation_ok() {
        operationHandler = new PurchaseOperationHandler();
        FruitTransaction purchaseTransaction =
                fruitTransactions.get(0);

        operationHandler.handleTransaction(purchaseTransaction);

        int actual = Storage.STORAGE.get("banana");
        int expected = 3;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void putNegativeBalanceAfterPurchaseOperation_notOk() {
        operationHandler = new PurchaseOperationHandler();
        FruitTransaction purchaseTransaction =
                fruitTransactions.get(0);
        FruitTransaction anotherPurchaseTransaction =
                fruitTransactions.get(0);

        operationHandler.handleTransaction(purchaseTransaction);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> operationHandler.handleTransaction(anotherPurchaseTransaction));
    }

    @Test
    void supplyOperation_ok() {
        operationHandler = new SupplyOperationHandler();
        FruitTransaction supplyTransaction = fruitTransactions.get(3);

        operationHandler.handleTransaction(supplyTransaction);

        int actual = Storage.STORAGE.get("banana");
        int expected = 13;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void supplyOperationWithNewFruit_ok() {
        operationHandler = new SupplyOperationHandler();
        FruitTransaction supplyTransaction = fruitTransactions.get(4);

        operationHandler.handleTransaction(supplyTransaction);

        int actual = Storage.STORAGE.get("apple");
        int expected = 6;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void balanceOperation_ok() {
        operationHandler = new BalanceOperationHandler();
        FruitTransaction balanceTransaction = fruitTransactions.get(1);

        operationHandler.handleTransaction(balanceTransaction);

        int actual = Storage.STORAGE.get("banana");
        int expected = 8;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void returnOperation_ok() {
        operationHandler = new ReturnOperationHandler();
        FruitTransaction returnTransaction = fruitTransactions.get(2);

        operationHandler.handleTransaction(returnTransaction);

        int actual = Storage.STORAGE.get("banana");
        int expected = 15;

        Assertions.assertEquals(expected, actual);
    }
}

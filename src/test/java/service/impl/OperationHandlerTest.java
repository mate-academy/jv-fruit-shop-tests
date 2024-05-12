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

class OperationHandlerTest {
    private static List<FruitTransaction> fruitTransactions;
    private OperationHandler operationHandler;

    @BeforeAll
    static void initFruitTransactionList() {
        fruitTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 10),
                new FruitTransaction(Operation.PURCHASE, "banana", 7),
                new FruitTransaction(Operation.SUPPLY, "banana", 5),
                new FruitTransaction(Operation.RETURN, "banana", 10),
                new FruitTransaction(Operation.RETURN, "apple", 6)
        );
    }

    @BeforeEach
    void addFruitBalanceToStorage() {
        Storage.STORAGE.put("banana", 9);
    }

    @AfterAll
    static void clearStorage() {
        Storage.STORAGE.clear();
    }
    
    @Test
    void handleBalanceTransaction_ok() {
        operationHandler = new BalanceOperationHandler();
        operationHandler.handleTransaction(fruitTransactions.get(0));

        String fruit = "banana";
        Integer actual = Storage.STORAGE.get(fruit);

        Assertions.assertEquals(10, actual);
    }

    @Test
    void handleReturnTransaction_ok() {
        operationHandler = new ReturnOperationHandler();
        operationHandler.handleTransaction(fruitTransactions.get(3));
        String fruit = "banana";
        Integer actual = Storage.STORAGE.get(fruit);

        Assertions.assertEquals(19, actual);
    }

    @Test
    void handleSupplyTransaction_ok() {
        operationHandler = new SupplyOperationHandler();
        operationHandler.handleTransaction(fruitTransactions.get(2));

        String fruit = "banana";
        Integer actual = Storage.STORAGE.get(fruit);

        Assertions.assertEquals(14, actual);
    }

    @Test
    void handleSupplyTransactionForNewFruit_ok() {
        operationHandler = new SupplyOperationHandler();
        operationHandler.handleTransaction(fruitTransactions.get(4));

        String fruit = "apple";
        Integer actual = Storage.STORAGE.get(fruit);

        Assertions.assertEquals(6, actual);
    }

    @Test
    void handlePurchaseTransaction_ok() {
        operationHandler = new PurchaseOperationHandler();
        operationHandler.handleTransaction(fruitTransactions.get(1));

        String fruit = "banana";
        Integer actual = Storage.STORAGE.get(fruit);

        Assertions.assertEquals(2, actual);
    }

    @Test
    void handlePurchaseTransactionExpectNegativeBalance_notOk() {
        operationHandler = new PurchaseOperationHandler();

        operationHandler.handleTransaction(fruitTransactions.get(1));

        Assertions.assertThrows(
                RuntimeException.class,
                () -> operationHandler.handleTransaction(fruitTransactions.get(1)));
    }
}

package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionHandlerTest {
    private String apple = "apple";

    @BeforeEach
    void setUp() {
        Storage.storage.put("apple", 20);
    }

    @Test
    void balanceTransaction_valid_ok() {
        int appleForBalance = 10;
        int expectedForBalance = 10;
        FruitTransaction transaction = new FruitTransaction(
                Operation.BALANCE, apple, appleForBalance);
        TransactionHandler balanceTransactionHandler = new BalanceTransactionHandler();
        balanceTransactionHandler.process(transaction);
        assertEquals(expectedForBalance, (int) Storage.storage.get(apple));
    }

    @Test
    void purchaseTransaction_valid_ok() {
        int appleForPurchase = 5;
        int expectedForPurchase = 15;
        FruitTransaction transaction = new FruitTransaction(
                Operation.PURCHASE, apple, appleForPurchase);
        TransactionHandler purchaseTransactionHandler = new PurchaseTransactionHandler();
        purchaseTransactionHandler.process(transaction);
        assertEquals(expectedForPurchase, (int) Storage.storage.get(apple));
    }

    @Test
    void supplyTransaction_valid_ok() {
        int appleForSupply = 5;
        int expectedForSupply = 25;
        FruitTransaction transaction = new FruitTransaction(
                Operation.SUPPLY, apple, appleForSupply);
        TransactionHandler supplyTransactionHandler = new SupplyTransactionHandler();
        supplyTransactionHandler.process(transaction);
        assertEquals(expectedForSupply, (int) Storage.storage.get(apple));
    }

    @Test
    void returnTransaction_valid_ok() {
        int appleForReturn = 10;
        int expectedForReturn = 30;
        FruitTransaction transaction = new FruitTransaction(
                Operation.RETURN, apple, appleForReturn);
        TransactionHandler returnTransactionHandler = new ReturnTransactionHandler();
        returnTransactionHandler.process(transaction);
        assertEquals(expectedForReturn, (int) Storage.storage.get(apple));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}

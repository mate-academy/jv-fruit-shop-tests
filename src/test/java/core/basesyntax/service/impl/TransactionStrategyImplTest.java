package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.TransactionStrategy;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.strategy.handler.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int QUANTITY = 20;
    private static final String FRUIT_NAME2 = "apple";
    private static final int QUANTITY2 = 30;
    private static final int LIST_SIZE = 1;
    private static Map<Transaction.Operation, TransactionHandler> operationStrategies
            = new HashMap<>();
    private static TransactionStrategy transactionStrategy;
    private static Transaction balanceTransaction;
    private static Transaction purchaseTransaction;
    private static Transaction supplyTransaction;
    private static Transaction returnTransaction;
    private List<Transaction> transactions;

    @BeforeClass
    public static void setUpBeforeClass() {
        operationStrategies.put(Transaction.Operation.BALANCE, new BalanceHandler());
        operationStrategies.put(Transaction.Operation.PURCHASE, new PurchaseHandler());
        operationStrategies.put(Transaction.Operation.SUPPLY, new SupplyHandler());
        operationStrategies.put(Transaction.Operation.RETURN, new ReturnHandler());
        transactionStrategy = new TransactionStrategyImpl(operationStrategies);
        balanceTransaction = new Transaction(Transaction.Operation.BALANCE, FRUIT_NAME,QUANTITY);
        purchaseTransaction = new Transaction(Transaction.Operation.PURCHASE, FRUIT_NAME,QUANTITY);
        supplyTransaction = new Transaction(Transaction.Operation.SUPPLY, FRUIT_NAME,QUANTITY);
        returnTransaction = new Transaction(Transaction.Operation.RETURN, FRUIT_NAME,QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void process_transactionsIsNull() {
        transactionStrategy.process(null);
    }

    @Test(expected = RuntimeException.class)
    public void process_transactionsIsEmpty() {
        transactions = new ArrayList<>();
        transactionStrategy.process(transactions);
    }

    @Test(expected = RuntimeException.class)
    public void process_transactionIsNull() {
        transactions = List.of(null);
        transactionStrategy.process(transactions);
    }

    @Test
    public void process_multipleBalanceTransactions_ok() {
        Transaction balanceTransaction2 = new Transaction(Transaction.Operation.BALANCE,
                FRUIT_NAME2, QUANTITY2);
        transactions = List.of(balanceTransaction, balanceTransaction2);
        transactionStrategy.process(transactions);
        assertEquals(transactions.size(), Storage.fruitStorage.size());
        assertTrue(Storage.fruitStorage.containsKey(FRUIT_NAME));
        assertTrue(Storage.fruitStorage.containsKey(FRUIT_NAME2));
    }

    @Test
    public void process_BalanceTransactionNewFruit_ok() {
        transactions = List.of(balanceTransaction);
        transactionStrategy.process(transactions);
        assertEquals(transactions.size(), Storage.fruitStorage.size());
        assertEquals(QUANTITY, (int) Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void process_BalanceTransactionExistedFruit_notOk() {
        Storage.fruitStorage.put(FRUIT_NAME, QUANTITY);
        transactionStrategy.process(List.of(balanceTransaction));
    }

    @Test(expected = RuntimeException.class)
    public void process_PurchaseTransactionsNonExistedFruit_notOk() {
        Storage.fruitStorage.put(FRUIT_NAME2, QUANTITY);
        transactionStrategy.process(List.of(purchaseTransaction));
    }

    @Test(expected = RuntimeException.class)
    public void process_PurchaseTransactionsNotEnoughQuantity_notOk() {
        Storage.fruitStorage.put(FRUIT_NAME, QUANTITY);
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE,
                FRUIT_NAME, QUANTITY2);
        transactionStrategy.process(List.of(transaction));
    }

    @Test(expected = RuntimeException.class)
    public void process_PurchaseTransactionsEmptyStore_notOk() {
        transactionStrategy.process(List.of(purchaseTransaction));
    }

    @Test
    public void process_PurchaseTransactionValid_ok() {
        Storage.fruitStorage.put(FRUIT_NAME, QUANTITY2);
        transactionStrategy.process(List.of(purchaseTransaction));
        int expectedQuantity = QUANTITY2 - QUANTITY;
        assertEquals(expectedQuantity, (int) Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test
    public void process_PurchaseTransactionExactQuantity_ok() {
        Storage.fruitStorage.put(FRUIT_NAME, QUANTITY);
        transactionStrategy.process(List.of(purchaseTransaction));
        assertTrue(Storage.fruitStorage.isEmpty());
    }

    @Test
    public void process_ReturnTransactionNewFruit_ok() {
        transactions = List.of(returnTransaction);
        transactionStrategy.process(transactions);
        assertEquals(transactions.size(), Storage.fruitStorage.size());
        assertEquals(QUANTITY,(int) Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test
    public void process_ReturnTransactionExistedFruit_ok() {
        Storage.fruitStorage.put(FRUIT_NAME, QUANTITY);
        transactions = List.of(returnTransaction);
        transactionStrategy.process(transactions);
        int expectedQuantity = QUANTITY + QUANTITY;
        assertEquals(LIST_SIZE, Storage.fruitStorage.size());
        assertEquals(expectedQuantity,(int) Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test
    public void process_SupplyTransactionNewFruit_ok() {
        transactions = List.of(supplyTransaction);
        transactionStrategy.process(transactions);
        assertEquals(transactions.size(), Storage.fruitStorage.size());
        assertEquals(QUANTITY,(int) Storage.fruitStorage.get(FRUIT_NAME));
    }

    @Test
    public void process_SupplyTransactionExistedFruit_ok() {
        Storage.fruitStorage.put(FRUIT_NAME, QUANTITY);
        transactions = List.of(supplyTransaction);
        transactionStrategy.process(transactions);
        int expectedQuantity = QUANTITY + QUANTITY;
        assertEquals(LIST_SIZE, Storage.fruitStorage.size());
        assertEquals(expectedQuantity,(int) Storage.fruitStorage.get(FRUIT_NAME));
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}

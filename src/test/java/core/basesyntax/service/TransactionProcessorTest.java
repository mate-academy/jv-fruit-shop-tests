package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorTest {
    private static List<Transaction> transactions;
    private static TransactionProcessor processor;
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(Operation.BALANCE, new BalanceHandler());
        operationMap.put(Operation.SUPPLY, new SupplyHandler());
        operationMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationMap.put(Operation.RETURN, new ReturnHandler());
        strategy = new OperationStrategyImpl(operationMap);
        processor = new TransactionProcessorImpl(strategy);
        transactions = new ArrayList<>();
    }

    @BeforeEach
    void beforeEach() {
        Storage.STORAGE.clear();
        transactions.clear();
    }

    @Test
    void process_withValidTransactionsList_ok() {
        Transaction transaction1 = new Transaction();
        transaction1.setFruitOperation(Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setValue(20);
        Transaction transaction2 = new Transaction();
        transaction2.setFruitOperation(Operation.SUPPLY);
        transaction2.setFruit("banana");
        transaction2.setValue(50);
        Transaction transaction3 = new Transaction();
        transaction3.setFruitOperation(Operation.PURCHASE);
        transaction3.setFruit("banana");
        transaction3.setValue(30);
        Transaction transaction4 = new Transaction();
        transaction4.setFruitOperation(Operation.RETURN);
        transaction4.setFruit("banana");
        transaction4.setValue(10);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        processor.processTransaction(transactions);
        assertEquals("[banana=50]", Storage.STORAGE.entrySet().toString());
    }

    @Test
    void process_purchaseWithNotEnoughValue_notOk() {
        Transaction transaction1 = new Transaction();
        transaction1.setFruitOperation(Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setValue(20);
        Transaction transaction2 = new Transaction();
        transaction2.setFruitOperation(Operation.PURCHASE);
        transaction2.setFruit("banana");
        transaction2.setValue(21);
        transactions.add(transaction1);
        transactions.add(transaction2);
        assertThrows(RuntimeException.class,
                () -> processor.processTransaction(transactions));
    }

    @Test
    void process_purchaseFewTimes_notOk() {
        Transaction transaction1 = new Transaction();
        transaction1.setFruitOperation(Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setValue(20);
        Transaction transaction2 = new Transaction();
        transaction2.setFruitOperation(Operation.PURCHASE);
        transaction2.setFruit("banana");
        transaction2.setValue(10);
        Transaction transaction3 = new Transaction();
        transaction3.setFruitOperation(Operation.PURCHASE);
        transaction3.setFruit("banana");
        transaction3.setValue(10);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        processor.processTransaction(transactions);
        assertEquals("[banana=0]", Storage.STORAGE.entrySet().toString());
        Transaction transaction4 = new Transaction();
        transaction4.setFruitOperation(Operation.PURCHASE);
        transaction4.setFruit("banana");
        transaction4.setValue(10);
        transactions.add(transaction4);
        assertThrows(RuntimeException.class,
                () -> processor.processTransaction(transactions));
    }

    @Test
    void process_withTwoTypesOfFruit_ok() {
        Transaction transaction1 = new Transaction();
        transaction1.setFruitOperation(Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setValue(20);
        Transaction transaction2 = new Transaction();
        transaction2.setFruitOperation(Operation.BALANCE);
        transaction2.setFruit("apple");
        transaction2.setValue(30);
        Transaction transaction3 = new Transaction();
        transaction3.setFruitOperation(Operation.PURCHASE);
        transaction3.setFruit("banana");
        transaction3.setValue(5);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        processor.processTransaction(transactions);
        System.out.println(Storage.STORAGE.entrySet());
        assertEquals("[banana=15, apple=30]",
                Storage.STORAGE.entrySet().toString());
    }
}

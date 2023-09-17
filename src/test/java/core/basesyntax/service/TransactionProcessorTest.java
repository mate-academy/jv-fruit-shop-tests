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
    }

    @BeforeEach
    void beforeEach() {
        Storage.STORAGE.clear();
        processor = new TransactionProcessorImpl(strategy);
    }

    @Test
    void process_withValidTransactionsList_ok() {
        transactions = List.of(
                new Transaction(Operation.BALANCE, "banana", 20),
                new Transaction(Operation.SUPPLY, "banana", 50),
                new Transaction(Operation.PURCHASE, "banana", 30),
                new Transaction(Operation.RETURN, "banana", 10));
        processor.processTransaction(transactions);
        assertEquals("[banana=50]", Storage.STORAGE.entrySet().toString());
    }

    @Test
    void process_purchaseWithNotEnoughValue_notOk() {
        transactions = List.of(
                new Transaction(Operation.BALANCE, "banana", 20),
                new Transaction(Operation.PURCHASE, "banana", 21));
        assertThrows(RuntimeException.class,
                () -> processor.processTransaction(transactions));
    }

    @Test
    void process_purchaseFewTimes_notOk() {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(Operation.BALANCE, "banana", 20));
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 10));
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 10));
        processor.processTransaction(transactions);
        assertEquals("[banana=0]", Storage.STORAGE.entrySet().toString());
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 10));
        assertThrows(RuntimeException.class,
                () -> processor.processTransaction(transactions));
    }

    @Test
    void process_withTwoTypesOfFruit_ok() {
        transactions = List.of(
                new Transaction(Operation.BALANCE, "banana", 20),
                new Transaction(Operation.BALANCE, "apple", 30),
                new Transaction(Operation.PURCHASE, "banana", 5));
        processor.processTransaction(transactions);
        assertEquals("[banana=15, apple=30]",
                Storage.STORAGE.entrySet().toString());
    }
}

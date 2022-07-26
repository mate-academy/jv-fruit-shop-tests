package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransitionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;
    private final Fruit banana = new Fruit("banana");
    private final Fruit apple = new Fruit("apple");

    @BeforeClass
    public static void beforeClass() {
        transactionProcessor = new TransactionProcessorImpl(new OperationStrategyImpl());
        Storage.fruits.clear();
    }

    @Test
    public void process_Ok() {
        List<Transaction> transactions = getTransactionList();
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(banana, 110);
        expected.put(apple, 220);
        transactionProcessor.process(transactions);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals("Result of TransactionProcessorImpl.process doesn't equal to expected",
                expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void process_NullTransactionList_NotOk() {
        transactionProcessor.process(null);
        fail("Process method of NULL must throw NullPointerException exception");
    }

    @Test
    public void process_EmptyTransactionList_NotOk() {
        List<Transaction> transactions = new ArrayList<>();
        transactionProcessor.process(transactions);
        Map<Fruit, Integer> expected = new HashMap<>();
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals("Result of TransactionProcessorImpl.process doesn't equal to expected",
                expected, actual);
    }

    private List<Transaction> getTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(getTransaction(Operation.BALANCE, banana, 80));
        transactionList.add(getTransaction(Operation.BALANCE, apple, 160));
        transactionList.add(getTransaction(Operation.SUPPLY, banana, 40));
        transactionList.add(getTransaction(Operation.SUPPLY, apple, 80));
        transactionList.add(getTransaction(Operation.PURCHASE, banana, 20));
        transactionList.add(getTransaction(Operation.PURCHASE, apple, 40));
        transactionList.add(getTransaction(Operation.RETURN, banana, 10));
        transactionList.add(getTransaction(Operation.RETURN, apple, 20));

        return transactionList;
    }

    private Transaction getTransaction(Operation operation, Fruit fruit, Integer quantity) {
        Transaction transaction = new Transaction();
        transaction.setOperation(operation);
        transaction.setProduct(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}

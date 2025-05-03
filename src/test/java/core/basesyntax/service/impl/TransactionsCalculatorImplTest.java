package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.models.Fruit;
import core.basesyntax.models.Transaction;
import core.basesyntax.service.TransactionsCalculator;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionsCalculatorImplTest {
    private static TransactionsCalculator transactionsCalculator;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        transactionsCalculator = new TransactionsCalculatorImpl(storageDao);
    }

    @Test
    public void handleTransactions_balanceTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("b",
                new Fruit("Apple"), 20));
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 20;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void handleTransactions_supplyTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("s",
                new Fruit("Apple"), 20));
        Storage.storage.put(new Fruit("Apple"), 10);
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 30;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void handleTransactions_returnTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("r",
                new Fruit("Apple"), 20));
        Storage.storage.put(new Fruit("Apple"), 10);
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 30;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void handleTransactions_purchaseTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("p",
                new Fruit("Apple"), 20));
        Storage.storage.put(new Fruit("Apple"), 50);
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 30;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleTransactions_invalidTransaction_NotOk() {
        List<Transaction> transactionList = List.of(new Transaction("q",
                new Fruit("Apple"), 20));
        transactionsCalculator.handleTransactions(transactionList);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

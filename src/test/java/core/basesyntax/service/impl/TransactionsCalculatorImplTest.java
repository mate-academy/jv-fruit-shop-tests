package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
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
        StorageDaoImpl storageDaoImpl = new StorageDaoImpl();
        transactionsCalculator = new TransactionsCalculatorImpl(storageDaoImpl);
    }

    @Test
    public void handleTransactions_BallanceTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("b",
                new Fruit("Apple"), 20));
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 20;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
        Class<?> actualClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get(transactionList.get(0).getType()).getClass();
        Class<?> expectedClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get("b").getClass();
        assertEquals(actualClass, expectedClass);
    }

    @Test
    public void handleTransactions_SupplyTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("s",
                new Fruit("Apple"), 20));
        Storage.storage.put(new Fruit("Apple"), 10);
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 30;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
        Class<?> actualClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get(transactionList.get(0).getType()).getClass();
        Class<?> expectedClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get("s").getClass();
        assertEquals(actualClass, expectedClass);
    }

    @Test
    public void handleTransactions_ReturnTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("r",
                new Fruit("Apple"), 20));
        Storage.storage.put(new Fruit("Apple"), 10);
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 30;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
        Class<?> actualClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get(transactionList.get(0).getType()).getClass();
        Class<?> expectedClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get("r").getClass();
        assertEquals(actualClass, expectedClass);
    }

    @Test
    public void handleTransactions_PurchaseTransaction_Ok() {
        List<Transaction> transactionList = List.of(new Transaction("p",
                new Fruit("Apple"), 20));
        Storage.storage.put(new Fruit("Apple"), 50);
        transactionsCalculator.handleTransactions(transactionList);
        int expected = 30;
        int actual = Storage.storage.get(new Fruit("Apple"));
        assertEquals(expected, actual);
        Class<?> actualClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get(transactionList.get(0).getType()).getClass();
        Class<?> expectedClass = TransactionsCalculatorImpl.transactionHandlerMap
                .get("p").getClass();
        assertEquals(actualClass, expectedClass);
    }

    @Test
    public void handleTransactions_InvalidTransaction_NotOk() {
        List<Transaction> transactionList = List.of(new Transaction("q",
                new Fruit("Apple"), 20));
        assertNull(TransactionsCalculatorImpl.transactionHandlerMap
                .get(transactionList.get(0).getType()));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

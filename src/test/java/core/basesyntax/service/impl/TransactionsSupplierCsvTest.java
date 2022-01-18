package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.model.TransactionType;
import core.basesyntax.service.TransactionsSupplier;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TransactionsSupplierCsvTest {
    private static final String JOURNAL_FILEPATH = "src/test/resources/daily_journal.csv";
    private static final String NOTEXIST_FILEPATH = "src/test/resources/notexist.csv";
    private static final String BADFORMAT_FILEPATH = "src/test/resources/badfileformat.csv";
    private TransactionsSupplier transactionsSupplier;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTransactionsList_Ok() {
        transactionsSupplier = new TransactionsSupplierCsv(JOURNAL_FILEPATH);
        List<Transaction> expected = getExpectedTransactionList();
        List<Transaction> actual = transactionsSupplier.getTransactionsList();
        Assert.assertEquals("Returned List size", expected.size(), actual.size());
        for(int i = 0; i < expected.size(); i++) {
            Assert.assertEquals("List item " + i, expected.get(i), actual.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void getTransactionsList_FileNotFound() {
        transactionsSupplier = new TransactionsSupplierCsv(NOTEXIST_FILEPATH);
        List<Transaction> actual = transactionsSupplier.getTransactionsList();
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getTransactionsList_BadFileFormat() {
        transactionsSupplier = new TransactionsSupplierCsv(BADFORMAT_FILEPATH);
        List<Transaction> actual = transactionsSupplier.getTransactionsList();
    }
    private List<Transaction> getExpectedTransactionList() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(TransactionType.BALANCE, "banana", 20));
        expected.add(new Transaction(TransactionType.BALANCE, "apple", 100));
        expected.add(new Transaction(TransactionType.SUPPLY, "banana", 100));
        expected.add(new Transaction(TransactionType.PURCHASE, "banana", 13));
        expected.add(new Transaction(TransactionType.RETURN, "apple", 10));
        expected.add(new Transaction(TransactionType.PURCHASE, "apple", 20));
        expected.add(new Transaction(TransactionType.PURCHASE, "banana", 5));
        expected.add(new Transaction(TransactionType.SUPPLY, "banana", 50));
        return expected;
    }
}
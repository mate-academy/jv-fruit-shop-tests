package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final String VALID_TEXT_TRANSACTION1 = "b,banana,100";
    private static final String VALID_TEXT_TRANSACTION2 = "p,apple,10";
    private static final String INVALID_TRANSACTION_TYPE = "o,banana,2";
    private static final String INVALID_TRANSACTION_FORMAT = "banana,5";
    private static final String INVALID_TRANSACTION_QTY = "s,apple,banana";
    private FruitTransaction validTransation1;
    private FruitTransaction validTransation2;

    @Before
    public void beforeAll() {
        FruitTransaction.Operation transaction1Type = FruitTransaction.Operation.BALANCE;
        Fruit transaction1Fruit = new Fruit("banana");
        int transaction1Qty = 100;

        FruitTransaction.Operation transaction2Type = FruitTransaction.Operation.PURCHASE;
        Fruit transaction2Fruit = new Fruit("apple");
        int transaction2Qty = 10;

        validTransation1 = new FruitTransaction(
                transaction1Type, transaction1Fruit, transaction1Qty);
        validTransation2 = new FruitTransaction(
                transaction2Type, transaction2Fruit, transaction2Qty);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }

    @Test
    public void parse_validTransactions_Ok() {
        List<String> transactions = new ArrayList<>();
        transactions.add(VALID_TEXT_TRANSACTION1);
        transactions.add(VALID_TEXT_TRANSACTION2);

        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(validTransation1);
        expectedTransactions.add(validTransation2);

        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> parsedTransactions = transactionParser.parse(transactions);

        assertEquals(expectedTransactions, parsedTransactions);
        assertEquals(2, parsedTransactions.size());
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidTransactionType_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(INVALID_TRANSACTION_TYPE);

        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> parsedTransactions = transactionParser.parse(transactions);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidTransactionFormat_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(INVALID_TRANSACTION_FORMAT);

        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> parsedTransactions = transactionParser.parse(transactions);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidTransactionQty_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(INVALID_TRANSACTION_QTY);

        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> parsedTransactions = transactionParser.parse(transactions);
    }
}

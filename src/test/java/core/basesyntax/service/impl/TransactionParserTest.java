package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserTest {
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String bananaSupplyFileLine = "s,banana,100";
    private static final String bananaPurchaseFileLine = "p,banana,20";
    private static final String appleReturnFileLine = "r,apple,50";
    private static final String appleBalanceFileLine = "b,apple,40";
    private static Transaction bananaSupplyTransaction;
    private static Transaction bananaPurchaseTransaction;
    private static Transaction appleReturnTransaction;
    private static Transaction appleBalanceTransaction;
    private static TransactionParser transactionParser;
    private static List<Transaction> expectedTransactions;
    private static List<String> dataForParse;

    @BeforeClass
    public static void setUp() {
        transactionParser = new TransactionParserImpl();
        Fruit banana = new Fruit("banana");
        bananaSupplyTransaction = new Transaction();
        bananaSupplyTransaction.setFruit(banana);
        bananaSupplyTransaction.setOperation(Transaction.Operation.SUPPLY);
        bananaSupplyTransaction.setSum(100);
        bananaPurchaseTransaction = new Transaction();
        bananaPurchaseTransaction.setFruit(banana);
        bananaPurchaseTransaction.setOperation(Transaction.Operation.PURCHASE);
        bananaPurchaseTransaction.setSum(20);
        Fruit apple = new Fruit("apple");
        appleReturnTransaction = new Transaction();
        appleReturnTransaction.setFruit(apple);
        appleReturnTransaction.setOperation(Transaction.Operation.RETURN);
        appleReturnTransaction.setSum(50);
        appleBalanceTransaction = new Transaction();
        appleBalanceTransaction.setFruit(apple);
        appleBalanceTransaction.setOperation(Transaction.Operation.BALANCE);
        appleBalanceTransaction.setSum(40);
    }

    @Before
    public void beforeAll() {
        expectedTransactions = new ArrayList<>();
        dataForParse = new ArrayList<>();
    }

    @Test
    public void parseTransactionsList_correctData_ok() {
        expectedTransactions.add(bananaSupplyTransaction);
        expectedTransactions.add(bananaPurchaseTransaction);
        expectedTransactions.add(appleReturnTransaction);
        expectedTransactions.add(appleBalanceTransaction);
        dataForParse.add(FIRST_LINE);
        dataForParse.add(bananaSupplyFileLine);
        dataForParse.add(bananaPurchaseFileLine);
        dataForParse.add(appleReturnFileLine);
        dataForParse.add(appleBalanceFileLine);
        List<Transaction> actualTransactions = transactionParser.parse(dataForParse);
        Assert.assertEquals(expectedTransactions.toString(), actualTransactions.toString());
    }

    @Test
    public void parseTransactionsList_differentData_notOk() {
        expectedTransactions.add(bananaSupplyTransaction);
        expectedTransactions.add(appleReturnTransaction);
        expectedTransactions.add(appleBalanceTransaction);
        expectedTransactions.add(bananaPurchaseTransaction);
        dataForParse.add(FIRST_LINE);
        dataForParse.add(bananaSupplyFileLine);
        dataForParse.add(bananaPurchaseFileLine);
        dataForParse.add(appleReturnFileLine);
        dataForParse.add(appleBalanceFileLine);
        List<Transaction> actualTransactions = transactionParser.parse(dataForParse);
        Assert.assertNotEquals(expectedTransactions.toString(), actualTransactions.toString());
    }

    @Test
    public void parseTransactionsList_incorrectDataOrder_notOk() {
        expectedTransactions.add(appleReturnTransaction);
        expectedTransactions.add(appleBalanceTransaction);
        dataForParse.add(FIRST_LINE);
        dataForParse.add(bananaSupplyFileLine);
        dataForParse.add(bananaPurchaseFileLine);
        List<Transaction> actualTransactions = transactionParser.parse(dataForParse);
        Assert.assertNotEquals(expectedTransactions.toString(), actualTransactions.toString());
    }
}

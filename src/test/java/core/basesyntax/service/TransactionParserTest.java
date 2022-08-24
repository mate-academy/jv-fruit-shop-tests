package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionParserTest {
    private static TransactionParser transactionParser;
    private static List<String> transaktions = List.of("type,fruit,quantity","b,apple,10", "s,orange,20");
    private static FruitTransaction transaction1 = new FruitTransaction(FruitTransaction.Operation.BALANCE,
            "apple", 10);
    private static FruitTransaction transaction2 = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
            "orange", 20);

    @Before
    public void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void getTransactions_twoValidTransactions_Ok() {
        List<FruitTransaction> actualTransactionList = transactionParser.getTransactions(transaktions);
        List<FruitTransaction> expectedTransactionList = List.of(transaction1, transaction2);
        assertEquals(actualTransactionList, expectedTransactionList);
    }

    @Test (expected = RuntimeException.class)
    public void getTransactions_emptyString_NotOk() {
        List<String> emptyStringTransaktions = List.of("","","");
        transactionParser.getTransactions(emptyStringTransaktions);
    }
}
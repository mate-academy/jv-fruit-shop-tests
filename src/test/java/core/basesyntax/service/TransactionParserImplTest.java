package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;
    private static List<String> transactions =
            List.of("type,fruit,quantity", "b,apple,10", "s,orange,20");
    private static FruitTransaction appleTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10);
    private static FruitTransaction orangeTransaction =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 20);

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void getTransactions_twoValidTransactions_Ok() {
        List<FruitTransaction> actualTransactionList = transactionParser
                .getTransactions(transactions);
        List<FruitTransaction> expectedTransactionList =
                List.of(appleTransaction, orangeTransaction);
        assertEquals(actualTransactionList, expectedTransactionList);
    }

    @Test (expected = RuntimeException.class)
    public void getTransactions_emptyString_NotOk() {
        List<String> emptyStringTransaktions = List.of("","","");
        transactionParser.getTransactions(emptyStringTransaktions);
    }
}

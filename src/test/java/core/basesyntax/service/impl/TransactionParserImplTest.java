package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;
    private static List<String> TEST_DATA;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionParser = new TransactionParserImpl();
        TEST_DATA = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
    }

    @Test
    public void parseTransactionList_Ok() {
        final List<Transaction> actual = transactionParser.parseTransactionList(TEST_DATA);
        List<Transaction> expectedList = List.of(new Transaction("banana", 20),
                new Transaction("apple", 100), new Transaction("banana", 100),
                new Transaction("banana", 13), new Transaction("apple", 10),
                new Transaction("apple", 20), new Transaction("banana", 5),
                new Transaction("banana", 50));
        expectedList.get(0).setOperation(Transaction.Operation.BALANCE);
        expectedList.get(1).setOperation(Transaction.Operation.BALANCE);
        expectedList.get(2).setOperation(Transaction.Operation.SUPPLY);
        expectedList.get(3).setOperation(Transaction.Operation.PURCHASE);
        expectedList.get(4).setOperation(Transaction.Operation.RETURN);
        expectedList.get(5).setOperation(Transaction.Operation.PURCHASE);
        expectedList.get(6).setOperation(Transaction.Operation.PURCHASE);
        expectedList.get(7).setOperation(Transaction.Operation.SUPPLY);
        for (int i = 0; i < 8; i++) {
            assertEquals(expectedList.get(i).getOperation(), actual.get(i).getOperation());
        }
    }

    @Test(expected = RuntimeException.class)
    public void parse_InvalidStoreOperationTransaction_NotOk() {
        TEST_DATA.add("type,fruit,quantity");
        transactionParser.parseTransactionList(TEST_DATA);
    }
}
